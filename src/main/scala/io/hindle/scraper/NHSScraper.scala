package io.hindle.scraper

import grizzled.slf4j.Logger
import io.hindle.scraper.models.SymptomRecord
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import scala.collection.convert.ImplicitConversionsToScala._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

final class NHSScraper {
  private val logger = Logger(classOf[NHSScraper])
  private val baseUrl = "http://www.nhs.uk"

  /*
   * Retrieve all documents from the NHS Health Index A-Z.
   */
  private def healthIndexDocuments: Future[Seq[Document]] = {
    Future.traverse[Char, Document, Seq]('A' to 'Z') { letter =>
      val url = s"$baseUrl/conditions/pages/BodyMap.aspx?Index=$letter"
      Future {
        Jsoup.connect(url).get
      }
    }
  }

  /*
   * Parse health index documents to build URLs for each health condition.
   * Any duplicate references are removed.
   */
  private def getConditionURLs(documents: Seq[Document]): Seq[String] = {
    documents.flatMap { document =>
      document
        .select("#haz-mod5 > div > div > ul > li > a").iterator
        .map(_.attr("href"))
        .filter(_.matches("/conditions/.*"))
        .map(condition => s"$baseUrl${condition.trim.toLowerCase}?nobeta=true")
    }.distinct
  }

  /*
   * Fetch and parse the content of each health condition page.
   */
  private def getSymptomPages(urls: Seq[String]): Future[Seq[SymptomRecord]] = {
    Future.traverse(urls) { url =>
      Future {
        logger.info(s"Fetching page content for $url")
        val doc = Jsoup.connect(url).get

        val pageContent = doc.select("#aspnetForm > div.content-wrap.clear.healthaz.temp-a > div.row.clear > div.col.col-primary > div.main-content.healthaz-content.clear").text
        val title = doc.select("#aspnetForm > div.content-wrap.clear.healthaz.temp-a > div.healthaz-header.clear > h1").text

        logger.info(s"Finished fetching page content for $url")
        SymptomRecord(url, title.trim, pageContent)
      }
    }
  }

  def getSymptomData: Future[Seq[SymptomRecord]] = {
    healthIndexDocuments.flatMap { document =>
      getSymptomPages(getConditionURLs(document))
    }
  }
}

