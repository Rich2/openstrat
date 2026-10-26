/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import java.io.IOException
import scala.annotation.unchecked.uncheckedVariance

/** Report of successful HTML file write. */
class HtmlWritten(detailStr: String) extends FileWritten(detailStr)
{ override def reportTypeStr: String = "HTML File written"
  override def toString: String = "HtmlFileWritten" + detailStr.enParenth
}

object HtmlWritten
{ /** Factory apply method to construct [[HtmlWritten]] report. */
  def apply(detailStr: String): HtmlWritten = new HtmlWritten(detailStr)

  /** Implicit evidence / instance of [[ShowType]] for [[HtmlWritten]] */
  given namedTypeEv: ShowType[HtmlWritten] = new ShowFileWritten[HtmlWritten]
  { override val filePrefix: String = "HTML"
    override def typeStr: String = "HtmlFileWritten"
  }  
}

/** class for grouping the results of HTML file writing attempts. */
class HtmlWrittenResults(val failsArray: Array[IOException], val succsArray: Array[HtmlWritten], val subsArray: Array[HtmlWrittenResults],
  val dirStr: String) extends FileWrittenResults[IOException, HtmlWritten]
{ override def subs: RArr[HtmlWrittenResults] = new RArr(subsArray)
  override def reportStr: String = s"$numSuccs HTML file successfully written$dirStr2, $numFails IOExceptions."
}

object HtmlWrittenResults
{
  
}

/** Report of successful CSS file write. */
class CssWritten(detailStr: String) extends FileWritten(detailStr)
{ override def reportTypeStr: String = "CSS File written"
  override def toString: String = "CssFileWritten" + detailStr.enParenth
}

object CssWritten
{ /** Factory apply method to construct [[CssWritten]] report. */
  def apply(detailStr: String): CssWritten = new CssWritten(detailStr)
  
  given reportBuilder: ResultsBuilder[IOException, CssWritten, CssWrittenResults] =
    new ResultsBuilder[IOException, CssWritten, CssWrittenResults]
  { override def fromArrays(arrayE: Array[IOExc], arrayA: Array[CssWritten], arrayR: Array[CssWrittenResults]): CssWrittenResults =
      CssWrittenResults(arrayE, arrayA, arrayR)
  }

  /** Implicit evidence / instance of [[ShowType]] for [[CssWritten]] */
  given namedTypeEv: ShowType[CssWritten] = new ShowFileWritten[CssWritten]
  { override val filePrefix: String = "CSS"
    override def typeStr: String = "CssFileWritten"
  }
}

/** class for grouping the results of CSS file writing attempts. */
class CssWrittenResults(val failsArray: Array[IOException], val succsArray: Array[CssWritten], val subsArray: Array[CssWrittenResults], val dirStr: String)
  extends FileWrittenResults[IOException, CssWritten]
{ override def subs: RArr[FileWrittenResults[IOExc, CssWritten]] = new RArr(subsArray)  
  override def reportStr: String = s"$numSuccs CSS file successfully written$dirStr2, $numFails IOExceptions."
}

object CssWrittenResults
{ /** Factory apply method to construct [[CssWrittenResults]]. */
  def apply(failsArray: Array[IOException], succsArray: Array[CssWritten], subsArr: Array[CssWrittenResults], dirStr: String = ""):
    CssWrittenResults = new CssWrittenResults(failsArray, succsArray, subsArr, dirStr)
  
  def apply(dirStr: String, attempts: IOExcEither[CssWritten]*): CssWrittenResults =
  { val (arrayE, arrayA) = attempts.toArrays
    new CssWrittenResults(arrayE, arrayA, Array(), dirStr)
  }

  def apply(dirStr: String, attempts: Iterable[IOExcEither[CssWritten]]): CssWrittenResults ={
    val (arrayE, arrayA) = attempts.toArrays
    new CssWrittenResults(arrayE, arrayA, Array(), dirStr)
  }
}

/** Report of successful Jar file write. */
class JarFileWritten(detailStr: String) extends FileWritten(detailStr)
{ override def reportTypeStr: String = "Jar File written"
  override def toString: String = "JarFileWritten" + detailStr.enParenth
}

object JarFileWritten
{ /** Factory apply method to construct [[JarFileWritten]] report. */
  def apply(detailStr: String): JarFileWritten = new JarFileWritten(detailStr)

  /** Implicit evidence / instance of [[ShowType]] for [[JarFileWritten]] */
  given namedTypeEv: ShowType[JarFileWritten] = new ShowFileWritten[JarFileWritten]
  { override val filePrefix: String = "Jar"
    override def typeStr: String = "JarFileWritten"
  }
}

/** Report of successful POM file write. */
class PomFileWritten(detailStr: String) extends FileWritten(detailStr)
{ override def reportTypeStr: String = "POM File written"
  override def toString: String = "PomFileWritten" + detailStr.enParenth
}

object PomFileWritten
{ /** Factory apply method to construct [[PomFileWritten]] report. */
  def apply(detailStr: String): PomFileWritten = new PomFileWritten(detailStr)

  given namedTypeEv: ShowType[PomFileWritten] = new ShowFileWritten[PomFileWritten]
  { override val filePrefix: String = "POM"
    override def typeStr: String = "PomFileWritten"
  }
}

/** Report of successful JavaScript file write. */
class JsFileWritten(detailStr: String) extends FileWritten(detailStr)
{ override def reportTypeStr: String = "JavaScript File written"
  override def toString: String = "JsFileWritten" + detailStr.enParenth

  /** Converts this for a successful js.map file write. */
  def withMap: JsWithMapFilesWritten = JsWithMapFilesWritten(detailStr)
}

object JsFileWritten
{ /** Factory apply method to construct [[JsFileWritten]] report. */
  def apply(detailStr: String): JsFileWritten = new JsFileWritten(detailStr)

  /** Implicit evidence / instance of [[ShowType]] for [[JsFileWritten]] */
  given namedTypeEv: ShowType[JsFileWritten] = new ShowFileWritten[JsFileWritten]
  { override val filePrefix: String = "JavaScript"
    override def typeStr: String = "JsFileWritten"
  }
}

/** Report of successful JavaScript file write and its map file write. */
class JsmapFileWritten(detailStr: String) extends FileWritten(detailStr)
{ override def reportTypeStr: String = "JavaScript map File written"
  override def toString: String = "JsmapFileWritten" + detailStr.enParenth
}

/** Report of successful JavaScript file write. */
class JsWithMapFilesWritten(detailStr: String) extends JsFileWritten(detailStr)
{ override def reportTypeStr: String = "JavaScript with map Files written"
  override def toString: String = "JsmapFileWritten" + detailStr.enParenth
}