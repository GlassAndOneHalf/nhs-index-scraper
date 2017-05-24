package io.hindle.scraper.models

import org.scalatest.{Matchers, WordSpec}

class SymptomRecordSpec extends WordSpec with Matchers {
  "normalisedName" should {
    "return a string containing the title with all non-word characters replaced with _" in {
      val record = SymptomRecord("someUrl", "?'ti$tle!@", "someContent")

      record.normalisedName shouldBe "__ti_tle__"
    }

    "return a string retaining '.' characters" in {
      val record = SymptomRecord("someUrl", "?'ti..t£#le!@", "someContent")

      record.normalisedName shouldBe "__ti..t__le__"
    }

    "return a string retaining '-' characters" in {
      val record = SymptomRecord("someUrl", "?'ti-t£#le!-@", "someContent")

      record.normalisedName shouldBe "__ti-t__le_-_"
    }
  }
}
