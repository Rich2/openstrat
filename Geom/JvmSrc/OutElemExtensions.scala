/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import utiljvm.{ writeFile => wFile, writeHtml => wHtml, writeCss => wCss }

implicit class OutElemExtensions(thisElem: OutElem)
{ /** Writes out the "out" method on this [[OutElem]] to the given file name in the given directory. */
  def writeFile(dirPathStr: String, fileName: String): ErrBi[IOExc, FileWritten] = wFile(dirPathStr / fileName, thisElem.out)
}

implicit class OutElemFileExtensions(thisOutFile: OutElemFile)
{ /** Writes out the "out" method on this [[OutElemFile]] to its file name in the given directory. */
  def writeFile(dirPathStr: String): ErrBi[IOExc, FileWritten] = wFile(dirPathStr / thisOutFile.fileName, thisOutFile.out)
}

implicit class HtmlPageFileExtensions(thisPage: HtmlPageFile)
{ /** Writes out the "out" method on this [[HtmlPageFile]] to its file name in the given directory. */
  def writeFile(dirPathStr: String): ErrBi[IOExc, HtmlFileWritten] = wHtml(dirPathStr / thisPage.fileName, thisPage.out)
}

implicit class CssRulesHolderExtensions(thisRules: CssRulesHolder)
{ /** Writes out the "out" method on this [[CssRulesHolder]] to the given file name in the given directory. */
  def writeFile(dirPathStr: String, fileNameStem: String): ErrBi[IOExc, CssFileWritten] = wCss(dirPathStr / fileNameStem, thisRules.out)
}

implicit class CssRulesFileExtensions(thisCssFile: CssRulesFile)
{ /** Writes out the "out" method on this [[CssRulesFile]] to its file name in the given directory. */
  def writeFile(dirPathStr: String): ErrBi[IOExc, CssFileWritten] = wCss(dirPathStr / thisCssFile.fileName, thisCssFile.out)
}