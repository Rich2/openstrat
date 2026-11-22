/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package utiljvm
import pweb.*

implicit class OutElemExtensions(thisElem: OutElem)
{ /** Writes out the "out" method on this [[OutElem]] to the given file name in the given directory. */
  def writeFile(dirPathStr: String, fileName: String): Either[IOExc, FileWritten] = pathStrWriteStr(dirPathStr / fileName, thisElem.out)
}

implicit class OutElemFileExtensions(thisOutFile: OutElemFile)
{ /** Writes out the "out" method on this [[OutElemFile]] to its file name in the given directory. */
  def writeFile(dirPathStr: String): Either[IOExc, FileWritten] = pathStrWriteStr(dirPathStr / thisOutFile.fileNameStr, thisOutFile.out)
}

/*implicit class HtmlPageFileExtensions(thisPage: HtmlPageFile)
{ /** Writes out the "out" method on this [[HtmlPageFile]] to its file name in the given directory. */
  def writeFile(dirPathStr: String): Either[IOExc, HtmlWritten] =
  {
    pathStrWriteStr(dirPathStr / thisPage.fileNameStr, thisPage.out).map(fw => HtmlWritten(dirPathStr))
  }
}*/

implicit class CssRulesHolderExtensions(thisRules: CssRulesHolder)
{ /** Writes out the "out" method on this [[CssRulesHolder]] to the given file name in the given directory. */
  /*def writeFileStem(dirPathStr: String, fileNameStem: String): Either[IOExc, CssWritten] =
  {
    val filePath = 
    pathStrWriteStr(dirPathStr / fileNameStem + ".css", thisRules.out).map(fw => CssWritten(dirPathStr))
  }*/

  /** Writes out the "out" method on this [[CssRulesHolder]] to the given file name in the given directory. */
  def writeFileFull(dirPath: DirsAbs, fileName: String): Either[IOExc, CssWritten] =
  { val filePath: DirsFileAbs = dirPath :/ fileName
    pathStrWriteStr(filePath.asStr, thisRules.out).map(_ => new CssWritten(filePath.arrayUnsafe))
  }
}

/*implicit class CssRulesFileExtensions(thisCssFile: CssRulesFile)
{ /** Writes out the "out" method on this [[CssRulesFile]] to its file name in the given directory. */
  def writeFile(dirPathStr: String): Either[IOExc, CssWritten] =
  {
    
    pathStrWriteStr(dirPathStr / thisCssFile.fileNameStr, thisCssFile.out).map(fw => CssWritten(dirPathStr))
  }
}*/