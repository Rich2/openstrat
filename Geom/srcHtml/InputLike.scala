/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import reflect.ClassTag

/** classes are used on the JVM to create user input and select elements in HTML pages. */
trait InputLike extends HtmlElem
{/** The [[String]] of the id attribute fot this input element. */
  def idStr: String

  def valueStr: String
  
  /** Other attributes in addition to the [[IdAtt]]. */
  def otherAttribs: RArr[HAtt]

  /** The ID attribute of this element. */
  def idAtt: IdAtt = IdAtt(idStr)
}

/** An HTML span containing a label and an input / select element. */
trait LabelInputLike extends SpanInlineBlockOwnline, Parent2T[HtmlElem]
{ /** [[String]] for the id attribute. */
  def idStr: String

  /** The label [[String]]. */
  def label: String

  override def child1: LabelHtml = LabelHtml(idStr, label)
  override def contents: RArr[XCon] = RArr(child1, child2)
}

/** An HTML input element. */
trait InputHtml extends InputLike, HtmlVoid
{ /** The type of input attribute. */
  def typeAtt: TypeAtt
  override def tagName: String = "input"

  /** The value attribute. */
  final def valueAtt: ValueAtt = ValueAtt(valueStr)  
}

/** HTML Input of type text.. */
trait InputStr extends InputHtml
{ override def typeAtt: TypeTextAtt.type = TypeTextAtt
}

case class LabelInputsLine(contents: RArr[XCon], otherAttribs: RArr[XCon]) extends SpanLine

object LabelInputsLine
{
  def apply(mems: LabelInputLike*)(using ct: ClassTag[HtmlInedit]): LabelInputsLine = new LabelInputsLine(mems.toRArr, RArr())
}