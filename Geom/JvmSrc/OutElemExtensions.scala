/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb; package webjvm
import utiljvm.{ writeFile => wFile }

implicit class OutElemExtensions(thisElem: OutElem)
{ /** Writes out the "out" method on this [[OutElem]] to the given file name in the given directory. */
  def writeFile(dirPathStr: String, fileName: String): ErrBi[IOExc, FileWritten] = wFile(dirPathStr / fileName, thisElem.out)
}

implicit class OutElemFileExtensions(thisOutFile: OutElemFile)
{ /** Writes out the "out" method on this [[OutElemFile]] to its file name in the given directory. */
  def writeFile(dirPathStr: String): ErrBi[IOExc, FileWritten] = wFile(dirPathStr / thisOutFile.fileNameStr, thisOutFile.out)
}

implicit class HtmlPageFileExtensions(thisPage: PageFile)
{ /** Writes out the "out" method on this [[PageFile]] to its file name in the given directory. */
  def writeFile(dirPathStr: String): ErrBi[IOExc, HtmlFileWritten] =
    wFile(dirPathStr / thisPage.fileNameStr, thisPage.out).map(fw => HtmlFileWritten(fw.detailStr))
}

implicit class CssRulesHolderExtensions(thisRules: CssRulesHolder)
{ /** Writes out the "out" method on this [[CssRulesHolder]] to the given file name in the given directory. */
  def writeFileStem(dirPathStr: String, fileNameStem: String): ErrBi[IOExc, CssFileWritten] =
    wFile(dirPathStr / fileNameStem + ".css", thisRules.out).map(fw => CssFileWritten(fw.detailStr))

  /** Writes out the "out" method on this [[CssRulesHolder]] to the given file name in the given directory. */
  def writeFileFull(dirPathStr: String, fileName: String): ErrBi[IOExc, CssFileWritten] =
    wFile(dirPathStr / fileName, thisRules.out).map(fw => CssFileWritten(fw.detailStr))
}

implicit class CssRulesFileExtensions(thisCssFile: CssRulesFile)
{ /** Writes out the "out" method on this [[CssRulesFile]] to its file name in the given directory. */
  def writeFile(dirPathStr: String): ErrBi[IOExc, CssFileWritten] =
    wFile(dirPathStr / thisCssFile.fileNameStr, thisCssFile.out).map(fw => CssFileWritten(fw.detailStr))
}