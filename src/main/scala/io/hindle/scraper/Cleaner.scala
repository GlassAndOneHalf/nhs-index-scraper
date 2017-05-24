package io.hindle.scraper

import io.hindle.scraper.models.SymptomRecord

object Cleaner {

  /**
    * Removes data that has an empty title/content.
    *
    * @param data A sequence of symptom records.
    * @return The same sequence, data, less the empty records.
    */
  def cleanSymptomData(data: Seq[SymptomRecord]): Seq[SymptomRecord] = {
    data.filter(record => record.title.nonEmpty && record.content.nonEmpty)
  }
}
