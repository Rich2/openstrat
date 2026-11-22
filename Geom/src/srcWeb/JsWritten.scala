/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import java.io.IOException, annotation.unchecked.uncheckedVariance, collection.mutable.ArrayBuffer

trait JsOrMapWritten extends FileWritten

/** Report of successful JavaScript file write. */
class JsWritten(val arrayUnsafe: Array[String]) extends JsOrMapWritten
{ /** Converts this for a successful js.map file write. */
  def withMap(operand: Either[IOException, JsMapWritten]): FileWrittenResults[IOExc, JsOrMapWritten] =
  { val eArray: Array[IOException] = operand match{
      case Right(_) => Array()
      case Left(err) => Array(err)
    }
    val sArray: Array[JsOrMapWritten] = operand match
    { case Right(jw2) => Array(this, jw2)
      case Left(_) => Array(this)
    }
    FileWrittenResults.fromArrays(eArray, sArray, Array())
  }  

  override def fileTypeStr: String = "JavaScript"
  override def classStr: String = "JsWritten"
}

object JsWritten
{ /** Factory apply method to construct [[JsWritten]] report. */
  def apply(filePath: DirsFileAbs): JsWritten = new JsWritten(filePath.arrayUnsafe)

  /** Implicit evidence / instance of [[ShowType]] for [[JsWritten]] */
  given namedTypeEv: ShowType[JsWritten] = new ShowFileWritten[JsWritten]
  { override val filePrefix: String = "JavaScript"
    override def typeStr: String = "JsFileWritten"
  }
}

/** Report of successful JavaScript file write and its map file write. */
class JsMapWritten(val arrayUnsafe: Array[String]) extends JsOrMapWritten
{ override def fileTypeStr: String = "JavaScript"
  override def classStr: String = "JsMapWritten"
}

object JsMapWritten
{
  def apply(filePath: DirsFileAbs): JsMapWritten = new JsMapWritten(filePath.arrayUnsafe)
}