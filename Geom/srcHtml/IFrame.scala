/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** HTML iframe element. */
trait IFrame extends HtmlElem
{ override def tagName: String = "iframe"

  /** The source for this [[IFrame]]. */
  def srcAtt: SrcAtt

  def heightAtt: HeightCss

  def widthAtt: WidthCss

  def otherAttribs: RArr[XAtt]

  override def attribs: RArr[XAtt] = RArr(widthAtt, heightAtt, srcAtt) ++ otherAttribs
  override def contents: RArr[XCon] = RArr()
}

object IFrame
{ /** Factory apply method to create an iframe HTML element. */
  def apply(srcStr: String, widthAtt: WidthCss, heightAtt: HeightCss, otherAttribs: RArr[XAtt]): IFrame = IFrameGen(srcStr, widthAtt, heightAtt, otherAttribs)

  /** Factory apply method to create an iframe HTML element. */
  def apply(srcStr: String, widthAtt: WidthCss, heightAtt: HeightCss, otherAttribs: XAtt*): IFrame = IFrameGen(srcStr, widthAtt, heightAtt, otherAttribs.toRArr)

  /** Creates an HTML iframe element with a 16:9 ratio from the height attribute.  */
  def h169(srcStr: String, heightAtt: HeightCss, otherAttribs: XAtt*): IFrame = IFrameGen(srcStr, heightAtt.widthAtt(16.0 / 9), heightAtt, otherAttribs.toRArr)

  /** Creates an HTML iframe element with a 16:9 ratio from the width attribute. */
  def w169(srcStr: String, widthAtt: WidthCss, otherAttribs: XAtt*): IFrame = IFrameGen(srcStr, widthAtt, widthAtt.heightAtt(9.0 /16), otherAttribs.toRArr)

  /** Implementation class for the gneral case of an HTML iframe element. */
  case class IFrameGen(srcStr: String, widthAtt: WidthCss, heightAtt: HeightCss, otherAttribs: RArr[XAtt]) extends IFrame, HtmlOwnLine
  { override def srcAtt: SrcAtt = SrcAtt(srcStr)
  }
}

/** HTML iframe element, with the width and height specified in pixels. */
class IFramePx(val srcStr: String, val widthNum: Double, val heightNum: Double, val otherAttribs: RArr[XAtt]) extends IFrame, HtmlOwnLine
{ override def srcAtt: SrcAtt = SrcAtt(srcStr)
  override def widthAtt: WidthCss = WidthPx(widthNum)
  override def heightAtt: HeightCss = HeightPx(heightNum)
}

object IFramePx
{ /** Factory apply method to create an iframe HTML element with width and height specified in pixels. */
  def apply(srcStr: String, widthNum: Double, heightNum: Double, otherAttribs: RArr[XAtt]): IFramePx = new IFramePx(srcStr, widthNum, heightNum, otherAttribs)

  /** Factory apply method to create an iframe HTML element with width and height specified in pixels. */
  def apply(srcStr: String, widthNum: Double, heightNum: Double, otherAttribs: XAtt*): IFramePx = IFramePx(srcStr, widthNum, heightNum, otherAttribs.toRArr)

  /** Creates an HTML iframe element with a 16:9 ratio from the height attributewith width and height specified in pixels.  */
  def h169(srcStr: String, heightNum: Double, otherAttribs: XAtt*): IFramePx = IFramePx(srcStr, heightNum * 16.0 / 9, heightNum, otherAttribs.toRArr)

  /** Creates an HTML iframe element with a 16:9 ratio from the width attribute with width and height specified in pixels. */
  def w169(srcStr: String, widthNum: Double, otherAttribs: XAtt*): IFramePx = IFramePx(srcStr, widthNum, widthNum * 9.0 /16, otherAttribs.toRArr)
}

sealed trait ReferrerPolicy extends XAttShort
{ override def name: String = "referrerpolicy"
}

case object ReferSowco extends ReferrerPolicy
{ override def valueStr: String = "strict-origin-when-cross-origin"
}

trait YouFrame extends IFrame
{ def referrerPolicy: ReferrerPolicy
  override def attribs: RArr[XAtt] = RArr(widthAtt, heightAtt, srcAtt, referrerPolicy) ++ otherAttribs
}

object YouFrame
{
  /** Creates an HTML iframe YouTube element with a 16:9 ratio from the height attributewith width and height specified in pixels. */
  def h169(srcStr: String, heightNum: Double, referrerPolicy: ReferrerPolicy = ReferSowco, otherAttribs: XAtt*): YouFrame =
    YouFrameGen(srcStr,  WidthPx(heightNum * 16.0 / 9), HeightPx(heightNum), referrerPolicy, otherAttribs.toRArr)

  case class YouFrameGen(srcStr: String, widthAtt: WidthCss, heightAtt: HeightCss, referrerPolicy: ReferrerPolicy, otherAttribs: RArr[XAtt]) extends YouFrame,
    HtmlOwnLine
  { override def srcAtt: SrcAtt = SrcAtt(srcStr)
  }
}