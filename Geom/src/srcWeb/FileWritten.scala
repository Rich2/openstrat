/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import java.io.IOException, annotation.unchecked.uncheckedVariance, reflect.ClassTag, collection.mutable.ArrayBuffer 

/** Report of successful file write. */
trait FileWritten extends DoneIO
{ def arrayUnsafe: Array[String]

  def fileTypeStr: String

  def classStr: String
  def filePath: FilePathAbs = new FilePathAbs(arrayUnsafe)

  final def filePathStr: String = filePath.asStr
  final def fileNameStr: String = arrayUnsafe.last
  final def dirPathStr: String = filePath.dirPathStr  
  final override def reportTypeStr: String = fileTypeStr -- "written"
  final override def toString: String = classStr + filePathStr.enParenth
  final override def reportStr: String = s"$fileNameStr written to $dirPathStr"
}

object FileWritten
{
  /** Factory apply method to construct [[FileWritten]] report. */
  def apply(dirPath: DirPathAbs): FileWritten = FileWrittenGen(dirPath.arrayUnsafe)
  
  /** Factory apply method to construct [[FileWritten]] report. */
  def pathStr(dirStr: String): FileWritten = new FileWrittenGen(dirStr.pathArray)

//  given namedTypeEv: ShowType[FileWritten] = new ShowFileWrittenGen[FileWritten]

  given resultsBuilderEv[E <: IOException, A <: FileWritten](using ctE: ClassTag[E], ctA: ClassTag[A]): ResultsBuilder[E, A, FileWrittenResults[E, A]] =
    new ResultsBuilder[E, A, FileWrittenResults[E, A]]
    { override def fromArrays(arrayE: Array[E], arrayA: Array[A], arrayR: Array[FileWrittenResults[E, A]]): FileWrittenResults[E, A] =
      FileWrittenResults.fromArrays(arrayE, arrayA, arrayR)
    }

  /** Report of successful file write. */
  case class FileWrittenGen(val arrayUnsafe: Array[String]) extends FileWritten  
  { override def fileTypeStr: String = "file"
    override def classStr: String = "FileWrittenGen"
  }
}

/** Trait for grouping the results of file writing attempts. */
trait FileWrittenResults[+E <: IOException, +A <: FileWritten] extends Results[E, A]
{ //def dirArray: Array[String]
  //def dirPath: DirsAbs = new DirsAbs(dirArray)
  def dirStr: String
  override def subs: RArr[FileWrittenResults[E, A]] @uncheckedVariance
  def dirStr2: String = ife(dirStr == "", "", s" to $dirStr")
  override def reportStr: String = s"$numSuccs files successfully written$dirStr2, $numFails IOExceptions"
}

object FileWrittenResults
{ 
  //def apply[+E <: IOException, +A <: FileWritten](dirStr: String)

  def errs[E <: IOException, A <: FileWritten](dirStr: String, errs: E*)(using ctE: ClassTag[E], ctA: ClassTag[A]): FileWrittenResults[E, A] =
    new FileWrittenResultsGen[E, A](errs.toArray, Array(), Array(), dirStr)
  
  def errArray[E <: IOException, A <: FileWritten](failsArray: Array[E], dirStr: String)(using ctE: ClassTag[E], ctA: ClassTag[A]): FileWrittenResults[E, A] =
    new FileWrittenResultsGen[E, A](failsArray, Array(), Array(), dirStr)
  
  /** Constructs [[FileWrittenResults]] from 3 [[Array]]s. */
  def fromArrays[E <: IOException, A <: FileWritten](failsArr: Array[E], succsArr: Array[A], subsArr: Array[FileWrittenResults[E, A]], dirStr: String = ""):
  FileWrittenResults[E, A] = FileWrittenResultsGen(failsArr, succsArr, subsArr, dirStr)
  
  def fromSeq[E <: IOException, A <: FileWritten](dirStr: String, input: Seq[Either[E, A]])(using ClassTag[E], ClassTag[A]): FileWrittenResults[E, A] =
  { val eBuff = new ArrayBuffer[E]()
    val aBuff = new ArrayBuffer[A]()
    input.foreach {
      case Left(err) => eBuff.append(err)
      case Right(rep) => aBuff.append(rep)      
    }
    new FileWrittenResultsGen[E, A](eBuff.toArray, aBuff.toArray, Array(), dirStr)
  }

  def combine[E <: IOException, A <: FileWritten](dirStr: String, input: FileWrittenResults[E, A]*)(using ctE: ClassTag[E], ctA: ClassTag[A]): FileWrittenResults[E, A] =
  { val eBuff: ArrayBuffer[E] = new ArrayBuffer[E]()
    val aBuff: ArrayBuffer[A] = new ArrayBuffer[A]()
    val rBuff: ArrayBuffer[FileWrittenResults[E, A]] = new ArrayBuffer[FileWrittenResults[E, A]]()
    input.foreach { res =>
      res.fails.foreach(eBuff.append(_))
      res.succs.foreach(aBuff.append(_))
      res.subs.foreach((sub: FileWrittenResults[E, A]) => rBuff.append(sub))
    }
    FileWrittenResults.fromArrays(eBuff.toArray, aBuff.toArray, rBuff.toArray, dirStr)
  }

  def combine[E <: IOException, A <: FileWritten](dirStr: String, input: RArr[FileWrittenResults[E, A]])(using ctE: ClassTag[E], ctA: ClassTag[A]): FileWrittenResults[E, A] ={
    val eBuff: ArrayBuffer[E] = new ArrayBuffer[E]()
    val jBuff: ArrayBuffer[A] = new ArrayBuffer[A]()
    val rBuff: ArrayBuffer[FileWrittenResults[E, A]] = new ArrayBuffer[FileWrittenResults[E, A]]()
    input.foreach { res =>
      res.fails.foreach(eBuff.append(_))
      res.succs.foreach(jBuff.append(_))
      res.subs.foreach((sub: FileWrittenResults[E, A]) => rBuff.append(sub))
    }
    FileWrittenResults.fromArrays(eBuff.toArray, jBuff.toArray, rBuff.toArray, dirStr)
  }


  /** Implementation class for the general case of [[FileWrittenResults]]. */
  class FileWrittenResultsGen[+E <: IOException, +A <: FileWritten](val failsArray: Array[E] @uncheckedVariance, val succsArray: Array[A] @uncheckedVariance,
    val subsArr: Array[FileWrittenResults[E, A] @uncheckedVariance], val dirStr: String) extends FileWrittenResults[E, A]
  { override def subs: RArr[FileWrittenResults[E, A]] = new RArr(subsArr)
  }
}

abstract class FileWrittenResultsBuilder[E <: IOException, A <: FileWritten, R <: FileWrittenResults[E, A]](using classTagE: ClassTag[E],
  classTagA: ClassTag[A], classTagR: ClassTag[R]) extends ResultsBuilder[E, A, R]
{ /** Constructs the given [[Results]] class from an [[Array]] of errors and an [[Array]] of successes. */
  def fromStrArrays(dirStr: String, arrayE: Array[E], arrayA: Array[A], arrayR: Array[R]): R
  
  /** Constructs the given [[Results]] class from a [[Sequ]] of [[Either]]s. */
  def fromSequ(dirStr: String, seq: Sequ[Either[E, A]]): R =
  { val eBuff: ArrayBuffer[E] = new ArrayBuffer[E]()
    val aBuff: ArrayBuffer[A] = new ArrayBuffer[A]()
    seq.foreach(_.fold(eBuff.append(_), aBuff.append(_)))
    fromStrArrays(dirStr, eBuff.toArray, aBuff.toArray, Array[R]())
  }
}

/** Report of successful text file write. */
class TextFileWritten(val arrayUnsafe: Array[String]) extends FileWritten
{ override def fileTypeStr: String = "text"
  override def classStr: String = "TextFileWritten"
}

object TextFileWritten
{ /** Factory apply method to construct [[TextFileWritten]] report. */
  def apply(filePath: FilePathAbs): TextFileWritten = new TextFileWritten(filePath.arrayUnsafe)

  /** Implicit evidence / instance of [[ShowType]] for [[TextFileWritten]] */
  given namedTypeEv: ShowType[TextFileWritten] = new ShowFileWritten[TextFileWritten]
  { override val filePrefix: String = "Text"
    override def typeStr: String = "TextFileWritten"
  }
}

/** Report of successful RSON file write. */
class RsonFileWritten(val arrayUnsafe: Array[String]) extends FileWritten
{ override def fileTypeStr: String = "RSON"
  override def classStr: String = "RsonFileWritten"
}

object RsonFileWritten
{ /** Factory apply method to construct [[RsonFileWritten]] report. */
  def apply(filePath: FilePathAbs): RsonFileWritten = new RsonFileWritten(filePath.arrayUnsafe)

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