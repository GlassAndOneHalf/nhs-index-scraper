package io.hindle.scraper.models

case class SymptomRecord(url: String, title: String, content: String) {
  /**
    * Provides a friendly, normalised, name for the symptom.
    *
    * @return A normalised name suitable for use in a file system.
    */
  def normalisedName: String = title.replaceAll("[^\\w.-]", "_")
}
