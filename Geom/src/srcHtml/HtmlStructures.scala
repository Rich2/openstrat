/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** An HTML Canvas element. */
case class CanvasHtml(attribs: RArr[HAtt], contents: RArr[XCon]) extends HtmlOwnLine
{ override def tagName: String = "canvas"
}

object CanvasHtml
{ /** Constructs an HTML canvas with an id attribute. */
  def id(idStr: String): CanvasHtml = new CanvasHtml(RArr(IdAtt(idStr)), RArr())

  /** Factory apply method for an HTML Canvas. */
  def apply(): CanvasHtml = new CanvasHtml(RArr(), RArr())
}

/** HTML P paragraph element. */
trait PHtml extends HtmlOwnLine
{ def tagName = "p"
  override def closeTagLine: Boolean = true
  def text(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen) = contents.foldLeft("")(_ + _.out(indent, line1InputLen, maxLineLen))
  def textLen: String = text(0, 0)
  override def toString: String = s"HtmlP $textLen characters, $attribsLen attributes"
}

object PHtml
{ /** Factory apply method for creating HTML paragraphs. */
  def apply(attribs: RArr[XAtt], contents: RArr[XCon]) = PHtmlGen(attribs, contents)

  /** Factory apply method for creating HTML paragraphs. */
  def apply(contents: XCon*) : PHtml = PHtmlGen(RArr(), contents.toRArr)

  /** Factory method for creating HTML paragraphs with an id attribute. There is a name overload that takes the content as an [[RArr]]. */
  def id(idStr: String, contents: XCon*): PHtml = PHtmlGen(RArr(IdAtt(idStr)), contents.toRArr)

  /** Factory method for creating HTML paragraphs with an id attribute. There is a name overload that takes the content as repeat parameters. */
  def id(idStr: String, contents: RArr[XCon]): PHtml = PHtmlGen(RArr(IdAtt(idStr)), contents)

  /** implementation  class for the general case of HTML P paragraph element. */
  case class PHtmlGen(attribs: RArr[HAtt], contents: RArr[XCon]) extends PHtml
}