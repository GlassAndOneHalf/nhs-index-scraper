package io.hindle.scraper.writers

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path, Paths}

import com.typesafe.config.ConfigFactory
import io.circe.generic.auto._
import io.circe.syntax._
import io.hindle.scraper.models.SymptomRecord

import scala.util.Try

object SymptomWriter {
  private lazy val directory = ConfigFactory.load().getString("output.directory")

  /**
    * Writes each symptom record as a JSON file in the specified directory.
    * You can configure the output directory in resources/application.conf
    *
    * @param data A sequence of symptom records.
    * @return A Failure object if writing fails. Success otherwise.
    */
  def write(data: Seq[SymptomRecord]): Try[Path] = Try {
    val dir = Files.createDirectories(Paths.get(directory))

    data.foreach { record =>
      val fileName = dir.resolve(s"${record.normalisedName}.json")
      Files.write(fileName, record.asJson.noSpaces.getBytes(StandardCharsets.UTF_8))
    }

    dir
  }
}
