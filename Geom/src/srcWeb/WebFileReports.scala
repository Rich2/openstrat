/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import java.io.IOException, annotation.unchecked.uncheckedVariance

/** Report of successful HTML file write. */
class HtmlWritten(detailStr: String) extends FileWritten(detailStr)
{ override def reportTypeStr: String = "HTML File written"
  override def toString: String = "HtmlFileWritten" + detailStr.enParenth
}

object HtmlWritten
{ /** Factory apply method to construct [[HtmlWritten]] report. */
  def apply(detailStr: String): HtmlWritten = new HtmlWritten(detailStr)

  /** [[ResultsBuilder]] type class instance / evidence for [[HtmlWritten]]. */
  given resultsBuilder: ResultsBuilder[IOException, HtmlWritten, HtmlWrittenResults] =
  new ResultsBuilder[IOException, HtmlWritten, HtmlWrittenResults]
  { override def fromArrays(arrayE: Array[IOExc], arrayA: Array[HtmlWritten], arrayR: Array[HtmlWrittenResults]): HtmlWrittenResults =
      HtmlWrittenResults.fromArrays(arrayE, arrayA, arrayR)
  }
  
  /** Implicit evidence / instance of [[ShowType]] for [[HtmlWritten]] */
  given namedTypeEv: ShowType[HtmlWritten] = new ShowFileWritten[HtmlWritten]
  { override val filePrefix: String = "HTML"
    override def typeStr: String = "HtmlFileWritten"
  }  
}

/** class for grouping the results of HTML file writing attempts. */
class HtmlWrittenResults(val failsArray: Array[IOException], val succsArray: Array[HtmlWritten], val subsArray: Array[HtmlWrittenResults], val dirStr: String)
  extends FileWrittenResults[IOException, HtmlWritten]
{ override def subs: RArr[HtmlWrittenResults] = new RArr(subsArray)
  override def reportStr: String = s"$numSuccs HTML file successfully written$dirStr2, $numFails IOExceptions."
}

object HtmlWrittenResults
{ /** Factory apply method to construct [[HtmlWrittenResults]]. */
  def apply(dirStr: String, attempts: IOExcEither[HtmlWritten]*): HtmlWrittenResults ={
    val (arrayE, arrayA) = attempts.toArrays
    new HtmlWrittenResults(arrayE, arrayA, Array(), dirStr)
  }

  /** Factory apply method to construct [[HtmlWrittenResults]]. */
  def apply(dirStr: String, attempts: Iterable[IOExcEither[HtmlWritten]]): HtmlWrittenResults ={
    val (arrayE, arrayA) = attempts.toArrays
    new HtmlWrittenResults(arrayE, arrayA, Array(), dirStr)
  }

  /** Factory method to construct [[HtmlWrittenResults]]. */
  def fromArrays(failsArray: Array[IOException], succsArray: Array[HtmlWritten], subsArr: Array[HtmlWrittenResults], dirStr: String = ""):
  HtmlWrittenResults = new HtmlWrittenResults(failsArray, succsArray, subsArr, dirStr)
}

/** Report of successful CSS file write. */
class CssWritten(detailStr: String) extends FileWritten(detailStr)
{ override def reportTypeStr: String = "CSS File written"
  override def toString: String = "CssFileWritten" + detailStr.enParenth
}

object CssWritten
{ /** Factory apply method to construct [[CssWritten]] report. */
  def apply(detailStr: String): CssWritten = new CssWritten(detailStr)

  /** [[ResultsBuilder]] type class instance / evidence for [[CssWritten]]. */
  given resultsBuilder: ResultsBuilder[IOException, CssWritten, CssWrittenResults] = new ResultsBuilder[IOException, CssWritten, CssWrittenResults]
  { override def fromArrays(arrayE: Array[IOExc], arrayA: Array[CssWritten], arrayR: Array[CssWrittenResults]): CssWrittenResults =
      CssWrittenResults.fromArrays(arrayE, arrayA, arrayR)
  }

  /** Implicit evidence / instance of [[ShowType]] for [[CssWritten]] */
  given showTypeEv: ShowType[CssWritten] = new ShowFileWritten[CssWritten]
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
  def apply(dirStr: String, attempts: IOExcEither[CssWritten]*): CssWrittenResults =
  { val (arrayE, arrayA) = attempts.toArrays
    new CssWrittenResults(arrayE, arrayA, Array(), dirStr)
  }

  /** Factory apply method to construct [[CssWrittenResults]]. */
  def apply(dirStr: String, attempts: Iterable[IOExcEither[CssWritten]]): CssWrittenResults ={
    val (arrayE, arrayA) = attempts.toArrays
    new CssWrittenResults(arrayE, arrayA, Array(), dirStr)
  }

  /** Factory method to construct [[CssWrittenResults]]. */
  def fromArrays(failsArray: Array[IOException], succsArray: Array[CssWritten], subsArr: Array[CssWrittenResults], dirStr: String = ""):
  CssWrittenResults = new CssWrittenResults(failsArray, succsArray, subsArr, dirStr)
}

/** Report of successful Jar file write. */
class JarWritten(detailStr: String) extends FileWritten(detailStr)
{ override def reportTypeStr: String = "Jar File written"
  override def toString: String = "JarFileWritten" + detailStr.enParenth
}

object JarWritten
{ /** Factory apply method to construct [[JarWritten]] report. */
  def apply(detailStr: String): JarWritten = new JarWritten(detailStr)

  /** Implicit evidence / instance of [[ShowType]] for [[JarWritten]] */
  given showTypeEv: ShowType[JarWritten] = new ShowFileWritten[JarWritten]
  { override val filePrefix: String = "Jar"
    override def typeStr: String = "JarFileWritten"
  }
}
/** class for grouping the results of Jar file writing attempts. */
class JarWrittenResults(val failsArray: Array[IOException], val succsArray: Array[JarWritten], val subsArray: Array[JarWrittenResults], val dirStr: String)
  extends FileWrittenResults[IOException, JarWritten]
{ override def subs: RArr[FileWrittenResults[IOExc, JarWritten]] = new RArr(subsArray)
  override def reportStr: String = s"$numSuccs Jar files successfully written$dirStr2, $numFails IOExceptions."
}

object JarWrittenResults
{ /** Factory apply method to construct [[JarWrittenResults]]. */
  def apply(dirStr: String, attempts: IOExcEither[JarWritten]*): JarWrittenResults =
  { val (arrayE, arrayA) = attempts.toArrays
    new JarWrittenResults(arrayE, arrayA, Array(), dirStr)
  }

  /** Factory apply method to construct [[JarWrittenResults]]. */
  def apply(dirStr: String, attempts: Iterable[IOExcEither[JarWritten]]): JarWrittenResults ={
    val (arrayE, arrayA) = attempts.toArrays
    new JarWrittenResults(arrayE, arrayA, Array(), dirStr)
  }

  /** Factory method to construct [[JarWrittenResults]]. */
  def fromArrays(failsArray: Array[IOException], succsArray: Array[JarWritten], subsArr: Array[JarWrittenResults], dirStr: String = ""):
  JarWrittenResults = new JarWrittenResults(failsArray, succsArray, subsArr, dirStr)
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
class JsWritten(detailStr: String) extends FileWritten(detailStr)
{ override def reportTypeStr: String = "JavaScript File written"
  override def toString: String = "JsFileWritten" + detailStr.enParenth

  /** Converts this for a successful js.map file write. */
  def withMap: JsWithMapFilesWritten = JsWithMapFilesWritten(detailStr)
}

object JsWritten
{ /** Factory apply method to construct [[JsWritten]] report. */
  def apply(detailStr: String): JsWritten = new JsWritten(detailStr)

  /** Implicit evidence / instance of [[ShowType]] for [[JsWritten]] */
  given namedTypeEv: ShowType[JsWritten] = new ShowFileWritten[JsWritten]
  { override val filePrefix: String = "JavaScript"
    override def typeStr: String = "JsFileWritten"
  }
}

/** class for grouping the results of JavaScript file writing attempts. */
class JsWrittenResults(val failsArray: Array[IOException], val succsArray: Array[JsWritten], val subsArray: Array[JsWrittenResults], val dirStr: String)
  extends FileWrittenResults[IOException, JsWritten]
{ override def subs: RArr[FileWrittenResults[IOExc, JsWritten]] = new RArr(subsArray)
  override def reportStr: String = s"$numSuccs JavaScript file successfully written$dirStr2, $numFails IOExceptions."
}

object JsWrittenResults
{ /** Factory apply method to construct [[JsWrittenResults]]. */
  def apply(dirStr: String, attempts: IOExcEither[JsWritten]*): JsWrittenResults =
  { val (arrayE, arrayA) = attempts.toArrays
    new JsWrittenResults(arrayE, arrayA, Array(), dirStr)
  }

  /** Factory apply method to construct [[JsWrittenResults]]. */
  def apply(dirStr: String, attempts: Iterable[IOExcEither[JsWritten]]): JsWrittenResults ={
    val (arrayE, arrayA) = attempts.toArrays
    new JsWrittenResults(arrayE, arrayA, Array(), dirStr)
  }
}

/** Report of successful JavaScript file write and its map file write. */
class JsmapFileWritten(detailStr: String) extends FileWritten(detailStr)
{ override def reportTypeStr: String = "JavaScript map File written"
  override def toString: String = "JsmapFileWritten" + detailStr.enParenth
}

/** Report of successful JavaScript file write. */
class JsWithMapFilesWritten(detailStr: String) extends JsWritten(detailStr)
{ override def reportTypeStr: String = "JavaScript with map Files written"
  override def toString: String = "JsmapFileWritten" + detailStr.enParenth
}