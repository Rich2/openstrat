/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import reflect.ClassTag

/** An HTML input element. */
trait InputHtml extends InputLike, HtmlVoid
{ /** The type of input attribute. */
  def typeAtt: TypeAtt
  override def tagName: String = "input"

  /** The value attribute. */
  final def valueAtt: ValueAtt = ValueAtt(valueStr)

  override def attribs: RArr[XAtt] = RArr(IdAtt(idStr), typeAtt, valueAtt) ++ otherAttribs
}

/** HTML Input element with submit type */
class SubmitInput(val idStr: String, val valueStr: String, val otherAttribs: RArr[XAtt]) extends InputHtml
{ override def typeAtt: TypeAtt = TypeSubmitAtt
  override def attribs: RArr[XAtt] = RArr(typeAtt, ValueAtt(valueStr)) ++ otherAttribs
}

object SubmitInput
{ /** #factory apply method for submit input HTML element. */
  def apply(idStr: String, valueStr: String, otherAttribs: RArr[XAtt] = RArr()): SubmitInput = new SubmitInput(idStr, valueStr, otherAttribs)
}

/** An HTML label followed by an [[UpdaterStrInput]]. */
class LabelStrInput(val idStr: String, val label: String, val valueStr: String)(using page: PageHtmlUpdater) extends LabelAndInput
{ override def child2: UpdaterStrInput = UpdaterStrInput(idStr, valueStr)
}

object LabelStrInput
{ /** Factory apply met hod to create an HTML label followed by an [[UpdaterStrInput]]. */
  def apply(idStr: String, label: String, valueStr: String)(using page: PageHtmlUpdater): LabelStrInput = new LabelStrInput(idStr, label, valueStr)
}

class LabelIntInput(val idStr: String, val label: String, val valueNum: Int, minVal: Int, maxVal: Int, step: Int = 1)(using page: PageHtmlUpdater) extends LabelAndInput
{ override def child2: UpdaterIntInput = UpdaterIntInput(idStr, valueNum, minVal, maxVal, step)
}

object LabelIntInput
{ /** Factory apply method for label and number input HTML elements. */
  def apply(idStr: String, label: String, valueNum: Int, minVal: Int, maxVal: Int, step: Int = 1)(using page: PageHtmlUpdater): LabelIntInput =
    new LabelIntInput(idStr, label, valueNum, minVal, maxVal, step)
}

class LabelDblInput(val idStr: String, val label: String, val valueNum: Double)(using page: PageHtmlUpdater) extends LabelAndInput
{ override def child2: UpdaterDblInput = UpdaterDblInput(idStr, valueNum)
}

object LabelDblInput
{ /** Factory apply method for label and number input HTML elements. */
  def apply(idStr: String, label: String, valueNum: Double)(using page: PageHtmlUpdater): LabelDblInput = new LabelDblInput(idStr, label, valueNum)
}

case class LabelInputsLine(contents: RArr[XCon], otherAttribs: RArr[XCon]) extends SpanLine

object LabelInputsLine
{
  def apply(mems: LabelAndInput*)(using ct: ClassTag[HtmlInedit]): LabelInputsLine = new LabelInputsLine(mems.toRArr, RArr())
}