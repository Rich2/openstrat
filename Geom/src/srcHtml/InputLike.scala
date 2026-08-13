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

/** Required attribute for HTML Form Inputs. */
case object RequiredAtt extends HAttNoValue("required")

/** An HTML span containing a label and an input / select element. Assigns the Input / Select element's id to the Label's For attribute. */
class LabelInput(val labelStr: String, val child2: InputLike, val otherAttribs: RArr[HAtt]) extends SpanInlineBlockOwnline, Parent2T[HtmlElem]
{ override def child1: LabelHtml = LabelHtml(labelStr, child2.idStr)
  override def attribs: RArr[HAtt] = super.attribs ++ otherAttribs
  override def contents: RArr[XCon] = RArr(child1, child2)
}

object LabelInput
{ /** Factory apply method to create an HTML Label and Input / Select Span. */
  def apply(labelStr: String, child2: InputLike, otherAttribs: RArr[HAtt]): LabelInput = new LabelInput(labelStr, child2, otherAttribs)

  /** Factory apply method to create an HTML Label and Input / Select Span. */
  def apply(labelStr: String, child2: InputLike, otherAttribs: HAtt*): LabelInput = new LabelInput(labelStr, child2, otherAttribs.toRArr)
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
  def apply(mems: LabelInput*)(using ct: ClassTag[HtmlInedit]): LabelInputsLine = new LabelInputsLine(mems.toRArr, RArr())
}