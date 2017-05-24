package io.hindle.scraper

import grizzled.slf4j.Logger
import io.hindle.scraper.writers.SymptomWriter

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

object Main extends App {
  val logger = Logger(this.getClass)
  val scraper = new NHSScraper

  logger.info("Scraping health index. This process may a few minutes.")

  val written = scraper.getSymptomData.map { data =>
    logger.info("Writing symptom records to directory...")

    SymptomWriter.write(Cleaner.cleanSymptomData(data)) match {
      case Success(path) => logger.info(s"Complete! Data written to $path")
      case Failure(e)    => logger.info(s"Error occurred whilst writing data. Exception: [$e]")
    }
  }

  Await.ready(written, 5 minutes)
}
