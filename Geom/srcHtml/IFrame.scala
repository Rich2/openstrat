/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

trait IFrame extends HtmlElem
{ override def tagName: String = "iframe"

  /** The source for this [[IFrame]]. */
  def srcAtt: SrcAtt

  def heightAtt: HeightCss

  def widthAtt: WidthCss

  def otherAttribs: RArr[XAtt]

  override def attribs: RArr[XAtt] = RArr(widthAtt, heightAtt, srcAtt)
  override def contents: RArr[XCon] = RArr()
}

object IFrame
{ /** Creates an HTML iframe element with a 16:9 ratio from the height attribute.  */
  def h169(srcStr: String, heightAtt: HeightCss, otherAttribs: XAtt*): IFrame = IFrameGen(srcStr, heightAtt.widthAtt(16.0 / 9), heightAtt, otherAttribs.toRArr)

  /** Creates an HTML iframe element with a 16:9 ratio from the width attribute. */
  def w169(srcStr: String, widthAtt: WidthCss, otherAttribs: XAtt*): IFrame = IFrameGen(srcStr, widthAtt, widthAtt.heightAtt(9.0 /16), otherAttribs.toRArr)

  case class IFrameGen(srcStr: String, widthAtt: WidthCss, heightAtt: HeightCss, otherAttribs: RArr[XAtt]) extends IFrame, HtmlOwnLine
  { override def srcAtt: SrcAtt = SrcAtt(srcStr)
  }
}