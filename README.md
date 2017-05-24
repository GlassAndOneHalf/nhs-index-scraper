NHS Index Scraper
====

Introduction
---
This application scrapes the NHS A-Z health index for data health issues and their 
associated symptoms. This data is saved in JSON format for consumption by other 
applications

Using the scraper
---
1. Select an output directory for the JSON files in `application.conf`. 

2. Ensure you have [SBT](http://www.scala-sbt.org) installed.

3. Navigate to the project in your favourite terminal application and enter `sbt run`

Potential Improvements
---
Due to time constraints, there are some improvements that could be made to this application.

- Tests for the webpage scraping. Scraping is inheriently unreliable as we have
no control over the content of the target website. Having tests to automatically detect
this would be useful.