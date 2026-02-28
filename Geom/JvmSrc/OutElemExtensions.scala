/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

implicit class OutElemExtensions(thisElem: OutElem)
{
  def writeFile(dirPathStr: String, fileName: String): ErrBi[IOExc, FileWritten] = utiljvm.writeFile(dirPathStr / fileName, thisElem.out)
}

implicit class OutElemFileExtensions(thisOutFile: OutElemFile)
{
  def writeFile(dirPathStr: String): ErrBi[IOExc, FileWritten] = utiljvm.writeFile(dirPathStr / thisOutFile.fileName, thisOutFile.out)
}

implicit class HtmlPageFileExtensions(thisPage: HtmlPageFile)
{
  def writeFile(dirPathStr: String): ErrBi[IOExc, HtmlFileWritten] = utiljvm.writeHtml(dirPathStr / thisPage.fileName, thisPage.out)
}

implicit class CssRulesHolderExtensions(thisRules: CssRulesHolder)
{
  def writeFile(dirPathStr: String, fileNameStem: String): ErrBi[IOExc, CssFileWritten] = utiljvm.writeCss(dirPathStr / fileNameStem + ".css", thisRules.out)
}