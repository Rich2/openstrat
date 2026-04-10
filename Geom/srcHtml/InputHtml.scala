/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import reflect.ClassTag

/** classes are used on the JVM to create user input and select elements in HTML pages. But are used in JavaScript to update the parts of the DOM registered
 * with that updater. */
trait InputHtmlLike extends HtmlElem
{/** The [[String]] of the id attribute. */
  def idStr: String

  /** The number of page elements that have registered to receive updates from this inout. */
  def clientCount: Int

  /** Other attributes in addition to the [[IDAtt]]. */
  def otherAttribs: RArr[XAtt]

  /** The ID attribute of this element. */
  def idAtt: IdAtt = IdAtt(idStr)
}

/** An HTML input element. */
trait InputHtml extends InputHtmlLike, HtmlVoid
{ def typeAtt: TypeAtt
  override def tagName: String = "input"

  final def valueAtt: ValueAtt = ValueAtt(valueStr)

  def valueStr: String

  override def attribs: RArr[XAtt] = RArr(IdAtt(idStr), typeAtt, valueAtt) ++ otherAttribs
}

/** A text callback from an input to a textContent. */
trait CallbackInput
{ /** The id attribute on the target HTML element whose textContent is to be updated. */
  def targetId: String
}

/** HTML Input element with submit type */
class SubmitInput(val idStr: String, val valueStr: String, val otherAttribs: RArr[XAtt]) extends InputHtml
{ override def typeAtt: TypeAtt = TypeSubmitAtt
  override def attribs: RArr[XAtt] = RArr(typeAtt, ValueAtt(valueStr)) ++ otherAttribs

  override def clientCount: Int = ???
}

object SubmitInput
{
  def apply(idStr: String, valueStr: String, otherAttribs: RArr[XAtt] = RArr()): SubmitInput = new SubmitInput(idStr, valueStr, otherAttribs)
}

/** An HTML span containing a label and an input / select element. */
trait LabelAndInput extends SpanInlineBlockOwnline, Parent2T[HtmlElem]
{ /** [[String]] for the id attribute. */
  def idStr: String
  
  /** The label [[String]]. */
  def label: String

  override def child1: HtmlLabel = HtmlLabel(idStr, label)
  override def contents: RArr[XCon] = RArr(child1, child2)
}

/** An HTML label followed by an [[UpdaterInputText]]. */
class LabelTextInput(val idStr: String, val label: String, val valueStr: String)(using page: PageHtmlUpdater) extends LabelAndInput
{ override def child2: UpdaterInputText = UpdaterInputText(idStr, valueStr)

}

object LabelTextInput
{ /** Factory apply met hod to create an HTML label followed by an [[UpdaterInputText]]. */
  def apply(idStr: String, label: String, valueStr: String)(using page: PageHtmlUpdater): LabelTextInput = new LabelTextInput(idStr, label, valueStr)
}

class LabelNumInput(val idStr: String, val label: String, val valueNum: Double)(using page: PageHtmlUpdater) extends LabelAndInput
{ override def child2: InputUpdaterNum = InputUpdaterNum(idStr, valueNum)
}

object LabelNumInput
{
  def apply(idStr: String, label: String, valueNum: Double)(using page: PageHtmlUpdater): LabelNumInput = new LabelNumInput(idStr, label, valueNum)
}

case class LabelInputsLine(contents: RArr[XCon], otherAttribs: RArr[XCon]) extends SpanLine

object LabelInputsLine
{
  def apply(mems: LabelAndInput*)(using ct: ClassTag[HtmlInedit]): LabelInputsLine = new LabelInputsLine(mems.toRArr, RArr())
}