/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import java.io.IOException, annotation.unchecked.uncheckedVariance, collection.mutable.ArrayBuffer

/** Report of successful JavaScript file write. */
class JsWritten(detailStr: String) extends FileWritten(detailStr)
{ override def reportTypeStr: String = "JavaScript File written"
  override def toString: String = "JsFileWritten" + detailStr.enParenth

  /** Converts this for a successful js.map file write. */
  def withMap(operand: Either[IOException, JsMapWritten]): JsWrittenResults =
  { val eArray: Array[IOException] = operand match{
      case Right(_) => Array()
      case Left(err) => Array(err)
    }
    val sArray: Array[JsWritten] = operand match{
      case Right(jw2) => Array(this, jw2)
      case Left(_) => Array(this)
    }
    JsWrittenResults.fromArrays(eArray, sArray, Array(), detailStr)
  }
}

object JsWritten
{ /** Factory apply method to construct [[JsWritten]] report. */
  def apply(detailStr: String): JsWritten = new JsWritten(detailStr)

  /** [[ResultsBuilder]] type class instance / evidence for [[JsWritteResults]]. */
  given resultsBuilder: ResultsBuilder[IOException, JsWritten, JsWrittenResults] = new ResultsBuilder[IOException, JsWritten, JsWrittenResults]
  { override def fromArrays(arrayE: Array[IOExc], arrayA: Array[JsWritten], arrayR: Array[JsWrittenResults]): JsWrittenResults =
      JsWrittenResults.fromArrays(arrayE, arrayA, arrayR)
  }

  /** Implicit evidence / instance of [[ShowType]] for [[JsWritten]] */
  given namedTypeEv: ShowType[JsWritten] = new ShowFileWritten[JsWritten]
  { override val filePrefix: String = "JavaScript"
    override def typeStr: String = "JsFileWritten"
  }

  class JsWrittenGen(detailStr: String) extends JsWritten(detailStr)
}

/** class for grouping the results of JavaScript file writing attempts. */
class JsWrittenResults(val failsArray: Array[IOException], val succsArray: Array[JsWritten], val subsArray: Array[JsWrittenResults], val dirStr: String)
  extends FileWrittenResults[IOException, JsWritten]
{ override def subs: RArr[JsWrittenResults] = new RArr(subsArray)
  override def reportStr: String = s"$numSuccs JavaScript file successfully written$dirStr2, $numFails IOExceptions."
}

object JsWrittenResults
{ /** Factory apply method to construct [[JsWrittenResults]]. */
  def apply(dirStr: String, attempts: IOExcEither[JsWritten]*): JsWrittenResults =
  { val (arrayE, arrayA) = attempts.toArrays
    new JsWrittenResults(arrayE, arrayA, Array(), dirStr)
  }

  /** Factory apply method to construct [[JsWrittenResults]]. */
  def apply(dirStr: String, attempts: Iterable[IOExcEither[JsWritten]]): JsWrittenResults =
  { val (arrayE, arrayA) = attempts.toArrays
    new JsWrittenResults(arrayE, arrayA, Array(), dirStr)
  }
  
  def errors(dirStr: String, input: IOException*): JsWrittenResults = new JsWrittenResults(input.toArray, Array(), Array(), dirStr)

  def combine(dirStr: String, input: JsWrittenResults*): JsWrittenResults =
  { val eBuff: ArrayBuffer[IOException] = new ArrayBuffer[IOException]()
    val jBuff: ArrayBuffer[JsWritten] = new ArrayBuffer[JsWritten]()
    val rBuff: ArrayBuffer[JsWrittenResults] = new ArrayBuffer[JsWrittenResults]()
    input.foreach{ res =>
      res.fails.foreach(eBuff.append(_))
      res.succs.foreach(jBuff.append(_))
      res.subs.foreach((sub: JsWrittenResults) => rBuff.append(sub))
    }
    new JsWrittenResults(eBuff.toArray, jBuff.toArray, rBuff.toArray, dirStr)
  }

  def combine(dirStr: String, input: RArr[JsWrittenResults]): JsWrittenResults ={
    val eBuff: ArrayBuffer[IOException] = new ArrayBuffer[IOException]()
    val jBuff: ArrayBuffer[JsWritten] = new ArrayBuffer[JsWritten]()
    val rBuff: ArrayBuffer[JsWrittenResults] = new ArrayBuffer[JsWrittenResults]()
    input.foreach{ resElem =>
      resElem.fails.foreach(eBuff.append(_))
      resElem.succs.foreach(jBuff.append(_))
      resElem.subs.foreach(sub =>rBuff.append(sub))
    }
    new JsWrittenResults(eBuff.toArray, jBuff.toArray, rBuff.toArray, dirStr)
  }

  /** Factory method to construct [[JsWrittenResults]]. */
  def fromArrays(failsArray: Array[IOException], succsArray: Array[JsWritten], subsArr: Array[JsWrittenResults], dirStr: String = ""): JsWrittenResults =
    new JsWrittenResults(failsArray, succsArray, subsArr, dirStr)
}

/** Report of successful JavaScript file write and its map file write. */
class JsMapWritten(detailStr: String) extends JsWritten(detailStr)
{ override def reportTypeStr: String = "JavaScript map File written"
  override def toString: String = "JsmapFileWritten" + detailStr.enParenth
}