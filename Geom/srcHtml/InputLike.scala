/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import reflect.ClassTag

/** classes are used on the JVM to create user input and select elements in HTML pages. But are used in JavaScript to update the parts of the DOM registered
 * with that updater. */
trait InputLike extends HtmlElem
{/** The [[String]] of the id attribute fot this input element. */
  def idStr: String

  def valueStr: String
  
  /** Other attributes in addition to the [[IdAtt]]. */
  def otherAttribs: RArr[XAtt]

  /** The ID attribute of this element. */
  def idAtt: IdAtt = IdAtt(idStr)
}

/** An HTML span containing a label and an input / select element. */
trait LabelAndInput extends SpanInlineBlockOwnline, Parent2T[HtmlElem]
{ /** [[String]] for the id attribute. */
  def idStr: String

  /** The label [[String]]. */
  def label: String

  override def child1: LabelHtml = LabelHtml(idStr, label)
  override def contents: RArr[XCon] = RArr(child1, child2)
}