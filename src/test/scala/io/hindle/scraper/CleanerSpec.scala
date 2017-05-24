package io.hindle.scraper

import io.hindle.scraper.models.SymptomRecord
import org.scalatest.{Matchers, WordSpec}

class CleanerSpec extends WordSpec with Matchers {
  "cleanSymptomData" should {
    "filter data containing an empty title field" in {
      val data = Seq(SymptomRecord("someUrl", "", "someContent"))

      Cleaner.cleanSymptomData(data) shouldBe empty
    }

    "filter data containing an empty content field" in {
      val data = Seq(SymptomRecord("someUrl", "", "someContent"))

      Cleaner.cleanSymptomData(data) shouldBe empty
    }

    "not filter valid data with non-empty title and content fields" in {
      val data = Seq(
        SymptomRecord("someUrl", "", "someContent"),
        SymptomRecord("someUrl", "someTitle", ""),
        SymptomRecord("someUrl", "someTitle", "someContent"))

      Cleaner.cleanSymptomData(data) should
        contain theSameElementsAs Seq(SymptomRecord("someUrl", "someTitle", "someContent"))
    }
  }
}
