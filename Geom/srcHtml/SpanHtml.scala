/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
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
  def text(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen) = contents.foldLeft("")(_ + _.out(indent, line1InputLen, maxLineLen))
  def textLen: String = text(0, 0)
  override def toString: String = s"HtmlSpan $textLen characters, $attribsLen attributes"
}

object SpanInlineInedit
{ /** Factory apply method for creating HTML span element. */
  def apply(strIn: String, attribs: XAtt*): SpanInlineInedit = new SpanInlineGen(RArr(strIn), attribs.toRArr)

  /** Factory apply method for creating HTML span element. */
  def apply(contents: RArr[XConInedit], attribs: RArr[XAtt]): SpanInlineInedit = new SpanInlineGen(contents, attribs)

  /** Factory method for creating HTML span element with an ID attribute. */
  def id(idStr: String, strIn: String, otherAttribs: XAtt*): SpanInlineInedit = new SpanInlineGen(RArr(strIn), IdAtt(idStr) %: otherAttribs.toRArr)

  /** Factory method for creating HTML span element with a class attribute. */
  def classAtt(classStr: String, strIn: String, otherAttribs: XAtt*): SpanInlineInedit = new SpanInlineGen(RArr(strIn), ClassAtt(classStr) %: otherAttribs.toRArr)

  /** Creates a inline span and registers the textContent with an HTML Text Input. */
  def inputText(input: InputUpdaterText)(f: String => String): SpanInlineInedit =
  { def newId = input.next1Id(f)
    new SpanInlineGen(RArr(f(input.valueStr)), RArr(newId))
  }

  def pink(str: String): SpanInlineInedit = new SpanInlineGen(RArr(str), RArr(StyleAtt(ColourDec(Colour.Pink))))

  case class SpanInlineGen(contents: RArr[XConInedit], attribs: RArr[XAtt]) extends SpanInlineInedit
}

/** HTML span element on its own line, with display set to block. */
trait SpanLine extends SpanHtml, HtmlOwnLineBlocked
{ def text(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen) = contents.foldLeft("")(_ + _.out(indent, line1InputLen, maxLineLen))
  def textLen: String = text(0, 0)
  override def toString: String = s"HtmlSpan $textLen characters, $attribsLen attributes"
}

object SpanLine
{ /** Factory apply method for creating HTML span element. */
  def apply(contents: XCon*): SpanHtml = new SpanLineGen(contents.toArr, RArr())

  /** Factory apply method for creating HTML span element. */
  def apply(contents: RArr[XConInedit], otherAttribs: RArr[XAtt]): SpanHtml = new SpanLineGen(contents, otherAttribs)

  /** Factory method for creating HTML span element with a display attribute. */
  def display(contents: XConInedit*)(otherDisplay: CssDec*): SpanHtml = new SpanLineGen(contents.toArr, RArr()){
    override def attribs: RArr[XAtt] = super.attribs +% StyleAtt(otherDisplay.toArr)
  }

  /** Factory method for creating HTML span element with a Style attribute with a colour declaration. */
  def colour(colour: Colour, contents: XConInedit*): SpanHtml = new SpanLineGen(contents.toArr, RArr())
  { override def attribs: RArr[XAtt] = super.attribs +% StyleAtt(ColourDec(colour))
  }

  /** Factory method to create a span line with a class attribute. */
  def classAtt(classStr: String, conStr: String, otherAttribs: XAtt*): SpanLine = new SpanLineGen(RArr(conStr), ClassAtt(classStr) %: otherAttribs.toArr)

  /** Creates a Bash line and registers the textContent with an HTML Text Input. */
  def inputText(input: InputUpdaterText)(f: String => String): SpanLine =
  { def newId = input.next1Id(f)
    new SpanLineGen(RArr(f(input.valueStr)), RArr(newId))
  }

  /** Creates a Bash line and registers the textContent with an HTML number Input. */
  def inputNum(input: InputUpdaterNum)(f: Double => String): SpanLine =
  { def newId = input.next1Id(f)
    new SpanLineGen(RArr(f(input.value)), RArr(newId))
  }

  /** HTML span element on its own line, with display set to block. */
  case class SpanLineGen(contents: RArr[XCon], otherAttribs: RArr[XAtt]) extends SpanLine
  { override def attribs: RArr[XAtt] = super.attribs ++ otherAttribs
  }
}