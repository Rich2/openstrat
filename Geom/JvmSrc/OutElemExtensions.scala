/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import utiljvm.{ writeFile => wFile }

implicit class OutElemExtensions(thisElem: OutElem)
{ /** Writes out the "out" method on this [[OutElem]] to the given file name in the given directory. */
  def writeFile(dirPathStr: String, fileName: String): ErrBi[IOExc, FileWritten] = wFile(dirPathStr / fileName, thisElem.out)
}

implicit class OutElemFileExtensions(thisOutFile: OutElemFile)
{ /** Writes out the "out" method on this [[OutElemFile]] to its file name in the given directory. */
  def writeFile(dirPathStr: String): ErrBi[IOExc, FileWritten] = wFile(dirPathStr / thisOutFile.fileName, thisOutFile.out)
}

implicit class HtmlPageFileExtensions(thisPage: PageFile)
{ /** Writes out the "out" method on this [[PageFile]] to its file name in the given directory. */
  def writeFile(dirPathStr: String): ErrBi[IOExc, HtmlFileWritten] = writeStrsHtml(dirPathStr / thisPage.fileName, thisPage.out)
}

implicit class CssRulesHolderExtensions(thisRules: CssRulesHolder)
{ /** Writes out the "out" method on this [[CssRulesHolder]] to the given file name in the given directory. */
  def writeFileStem(dirPathStr: String, fileNameStem: String): ErrBi[IOExc, CssFileWritten] = writeStrsCss(dirPathStr / fileNameStem + ".css", thisRules.out)

  /** Writes out the "out" method on this [[CssRulesHolder]] to the given file name in the given directory. */
  def writeFileFull(dirPathStr: String, fileName: String): ErrBi[IOExc, CssFileWritten] = writeStrsCss(dirPathStr / fileName, thisRules.out)
}

implicit class CssRulesFileExtensions(thisCssFile: CssRulesFile)
{ /** Writes out the "out" method on this [[CssRulesFile]] to its file name in the given directory. */
  def writeFile(dirPathStr: String): ErrBi[IOExc, CssFileWritten] = writeStrsCss(dirPathStr / thisCssFile.fileName, thisCssFile.out)
}