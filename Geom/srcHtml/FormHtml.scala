/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** HTML Form element. */
class FormHtml(val contents: RArr[XCon], val otherAttribs: RArr[XAtt]) extends HtmlTagLines
{ override def tagName: String = "form"  
  override def attribs: RArr[HAtt] = PostAtt %: otherAttribs
}

object FormHtml
{ /** Factory apply method to construct HTML Form element. There ia an apply name overload that takes the contents and other attributes as [[RArr]]s. */
  def apply(contents: XCon*): FormHtml = new FormHtml(contents.toRArr, RArr())
  
  /** Factory apply method to construct HTML Form element. There ia an apply name overload that takes the content as repeat parameters with no other
   * attributes.*/
  def apply(contents: RArr[XCon], otherAttribs: RArr[XAtt]): FormHtml = new FormHtml(contents, otherAttribs)  
}