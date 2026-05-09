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
{
  def r169(srcStr: String, height: HeightCss, otherAttribs: XAtt*): IFrame = IFrameGen(srcStr, ??? /*height * 16 / 9 */, height, otherAttribs.toRArr)

  case class IFrameGen(srcStr: String, widthAtt: WidthCss, heightAtt: HeightCss, otherAttribs: RArr[XAtt]) extends IFrame, HtmlOwnLine
  { override def srcAtt: SrcAtt = SrcAtt(srcStr)
  }
}

case class IFrame169(srcStr: String, heightAtt: HeightRotateableCss, otherAttribs: RArr[XAtt]) extends IFrame, HtmlOwnLine
{ override def srcAtt: SrcAtt = SrcAtt(srcStr)
  override def widthAtt: WidthRotateableCss = heightAtt.widthAtt(16.0 / 9)
}