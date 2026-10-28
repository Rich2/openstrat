/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import java.io.IOException, annotation.unchecked.uncheckedVariance, reflect.ClassTag; 

/** Report of successful file write. */
class FileWritten(val detailStr: String) extends DoneIO
{ override def reportTypeStr: String = "File written"
  override def toString: String = "FileWritten" + detailStr.enParenth
  override def reportStr: String = reportTypeStr -- "to" -- detailStr
}

object FileWritten
{ /** Factory apply method to construct [[FileWritten]] report. */
  def apply(detailStr: String): FileWritten = new FileWritten(detailStr)

  given namedTypeEv: ShowType[FileWritten] = new ShowFileWritten[FileWritten]
  { override def filePrefix: String = ""
  }

  given resultsBuilderEv[E <: IOException, A <: FileWritten](using ctE: ClassTag[E], ctA: ClassTag[A]): ResultsBuilder[E, A, FileWrittenResults[E, A]] =
    new ResultsBuilder[E, A, FileWrittenResults[E, A]]
    { override def fromArrays(arrayE: Array[E], arrayA: Array[A], arrayR: Array[FileWrittenResults[E, A]]): FileWrittenResults[E, A] =
      FileWrittenResults.fromArrays(arrayE, arrayA, arrayR)
    }
}

/** Trait for grouping the results of file writing attempts. */
trait FileWrittenResults[+E <: IOException, +A <: FileWritten] extends Results[E, A]
{ def dirStr: String
  override def subs: RArr[FileWrittenResults[E, A]] @uncheckedVariance
  def dirStr2: String = ife(dirStr == "", "", s" to $dirStr")
  override def reportStr: String = s"$numSuccs files successfully written$dirStr2, $numFails IOExceptions"
}

object FileWrittenResults
{ /** Constructs [[FileWrittenResults]] from 3 [[Array]]s. */
  def fromArrays[E <: IOException, A <: FileWritten](failsArr: Array[E], succsArr: Array[A], subsArr: Array[FileWrittenResults[E, A]]):
  FileWrittenResults[E, A] = FileWrittenResultsGen(failsArr, succsArr, subsArr, "")

  /** Implementation class for the general case of [[FileWrittenResults]]. */
  case class FileWrittenResultsGen[+E <: IOException, +A <: FileWritten](failsArray: Array[E] @uncheckedVariance, succsArray: Array[A] @uncheckedVariance,
    subsArr: Array[FileWrittenResults[E, A] @uncheckedVariance], dirStr: String) extends FileWrittenResults[E, A]
  { override def subs: RArr[FileWrittenResults[E, A]] = new RArr(subsArr)
  }
}

/** Report of successful text file write. */
class TextFileWritten(detailStr: String) extends FileWritten(detailStr)
{ override def reportTypeStr: String = "Text File written"
  override def toString: String = "TextFileWritten" + detailStr.enParenth
}

object TextFileWritten
{ /** Factory apply method to construct [[TextFileWritten]] report. */
  def apply(detailStr: String): TextFileWritten = new TextFileWritten(detailStr)

  /** Implicit evidence / instance of [[ShowType]] for [[TextFileWritten]] */
  given namedTypeEv: ShowType[TextFileWritten] = new ShowFileWritten[TextFileWritten]
  { override val filePrefix: String = "Text"
    override def typeStr: String = "TextFileWritten"
  }
}

/** Report of successful RSON file write. */
class RsonFileWritten(detailStr: String) extends FileWritten(detailStr)
{ override def reportTypeStr: String = "RSON File written"
  override def toString: String = "RSONFileWritten" + detailStr.enParenth
}

object RsonFileWritten
{ /** Factory apply method to construct [[RsonFileWritten]] report. */
  def apply(detailStr: String): RsonFileWritten = new RsonFileWritten(detailStr)

  /** Implicit evidence / instance of [[ShowType]] for [[RsonFileWritten]] */
  given namedTypeEv: ShowType[RsonFileWritten] = new ShowFileWritten[RsonFileWritten]
  { override val filePrefix: String = "RSON"
    override def typeStr: String = "RsonFileWritten"
  }
}

/** [[Show]] type class instances for file writing reports. Their main purpose is not to [[Persist]] [[ShowFileWritten]]s but to be able to accumulate and
 * summarise them. */
trait ShowFileWritten[A <: FileWritten] extends ShowEffectReport[A]
{ /** The file prefix such as HTML, Js or CSS. */
  def filePrefix: String

  override def typeStr: String = "FileWritten"
  override def strT(obj: A): String = typeStr

  override def actionStr(numSuccesses: Int): String =
  { val filePrefix2 = ife(filePrefix == "", "", filePrefix + " ")
    val fw: String = ife(numSuccesses == 1, "file", "files")
    s"$filePrefix2$fw written successfully"
  }
}