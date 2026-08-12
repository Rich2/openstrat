/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** HTML span element. */
trait SpanHtml extends HtmlElem
{ override def tagName = "span"
}

/** HTML inline-block span element, that is inlined in the editor. */
trait SpanInlineBlock extends SpanHtml, HtmlInlineBlocked

/** HTML inline-block span element, that is inlined in the editor. */
trait SpanInlineBlockInedit extends SpanInlineBlock, HtmlInlineBlockedInedit
{ def text(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen) = contents.foldLeft("")(_ + _.out(indent, line1InputLen, maxLineLen))
  def textLen: String = text(0, 0)
  override def toString: String = s"HtmlSpan $textLen characters, $attribsLen attributes"
}

/** HTML inline-block span element, that is on its own line in the editor. */
trait SpanInlineBlockOwnline extends SpanInlineBlock, HtmlOwnLine

/** HTML inline span element, used in its normal default inline manner. */
trait SpanInlineInedit extends SpanHtml, HtmlInedit
{
  def text(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String = contents.foldLeft("")(_ + _.out(indent, line1InputLen, maxLineLen))
  def textLen: String = text(0, 0)
  override def toString: String = s"HtmlSpan $textLen characters, $attribsLen attributes"
}

object SpanInlineInedit extends HtmlIneditCompanion[SpanInlineInedit]
{ /** Factory apply method for creating HTML span element. */
  def apply(contents: RArr[XConInedit], attribs: RArr[XAtt]): SpanInlineInedit = SpanInlineGen(contents, attribs)

  /** Implementation class for the general case of the [[SpanInlineInedit]] trait. */
  case class SpanInlineGen(contents: RArr[XConInedit], attribs: RArr[HAtt]) extends SpanInlineInedit
}

/** HTML span element on its own line, with display set to block. */
trait SpanLine extends SpanHtml, HtmlOwnLineBlocked
{ def text(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen) = contents.foldLeft("")(_ + _.out(indent, line1InputLen, maxLineLen))
  def textLen: String = text(0, 0)
  override def toString: String = s"HtmlSpan $textLen characters, $attribsLen attributes"
}

object SpanLine extends HtmlIneditCompanion[SpanLine]
{ /** Factory apply method for creating HTML span element. */
  override def apply(contents: RArr[XConInedit], attribs: RArr[XAtt]): SpanLine = SpanLineGen(contents, attribs)

  /** Factory method for creating HTML span element with a display attribute. */
  def display(contents: XConInedit*)(otherDisplay: CssDec*): SpanHtml = new SpanLineGen(contents.toArr, RArr())
  { override def attribs: RArr[HAtt] = super.attribs +% StyleAtt(otherDisplay.toArr)
  }
  
  /** HTML span element on its own line, with display set to block. */
  case class SpanLineGen(contents: RArr[XConInedit], otherAttribs: RArr[HAtt]) extends SpanLine
  { override def attribs: RArr[HAtt] = super.attribs ++ otherAttribs
  }
}