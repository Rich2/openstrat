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
  def text(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen) = contents.foldLeft("")(_ + _.out(indent, line1InputLen, maxLineLen))
  def textLen: String = text(0, 0)
  override def toString: String = s"HtmlSpan $textLen characters, $attribsLen attributes"
}

object SpanInlineInedit
{ /** Factory apply method for creating HTML span element. */
  def apply(strIn: String, attribs: XAtt*): SpanInlineInedit = SpanInlineGen(RArr(strIn), attribs.toRArr)

  /** Factory apply method for creating HTML span element. */
  def apply(contents: RArr[XConInedit], attribs: RArr[XAtt]): SpanInlineInedit = SpanInlineGen(contents, attribs)

  /** Factory method for creating HTML span element with an ID attribute. */
  def id(idStr: String, strIn: String, otherAttribs: XAtt*): SpanInlineInedit = SpanInlineGen(RArr(strIn), IdAtt(idStr) %: otherAttribs.toRArr)

  /** Factory method for creating HTML span element with a class attribute. */
  def classAtt(classStr: String, strIn: String, otherAttribs: XAtt*): SpanInlineInedit = SpanInlineGen(RArr(strIn), ClassAtt(classStr) %: otherAttribs.toRArr)

  /** Creates an inline span and registers the textContent with an HTML [[String]] Input. */
  def listenStrText(input: UpdaterStr)(f: String => String): SpanInlineInedit =
  { def newId = input.next1Text(f)
    new SpanInlineGen(RArr(f(input.valueStr)), RArr(newId))
  }

  /** Creates an inline span and registers the textContent with 2 HTML [[String]] Inputs. */
  def listen2StrText(input1: UpdaterStr, input2: UpdaterStr, otherAttribs: XAtt*)(f: (String, String) => String): SpanInlineInedit =
  { val idAtt: IdAtt = input1.next2Text1(input2, f)    
    SpanInlineGen(RArr(f(input1.valueStr, input2.valueStr)), RArr(idAtt))
  }

  /** Creates an inline span and registers the textContent with 3 HTML [[String]] Inputs. */
  def listen3StrText(input1: UpdaterStr, input2: UpdaterStr, input3: UpdaterStr, otherAttribs: XAtt*)(f: (String, String, String) => String): SpanInlineInedit =
  { val idAtt: IdAtt = input1.next3Id1(input2, input3, f)
    SpanInlineGen(RArr(f(input1.valueStr, input2.valueStr, input3.valueStr)), RArr(idAtt))
  }

  /** Creates an inline span and registers the textContent with 1 HTML [[String]] and 1 HTML number inputs. */
  def listenStrtNumText(input1: UpdaterStr, input2: UpdaterDblInput, otherAttribs: XAtt*)(f: (String, Double) => String): SpanInlineInedit =
  { val idAtt: IdAtt = input1.nextStrDblId1(input2, f)
    SpanInlineGen(RArr(f(input1.valueStr, input2.value)), RArr(idAtt))
  }

  /** Creates an inline span element and registers the textContent with an HTML [[Double]] Input. */
  def listenDblText(input: UpdaterDblInput)(f: Double => String): SpanInlineInedit =
  { val newId: IdAtt = input.next1(f)
    new SpanInlineGen(RArr(f(input.value)), RArr(newId))
  }

  def pink(str: String): SpanInlineInedit = new SpanInlineGen(RArr(str), RArr(StyleAtt(ColourDec(Colour.Pink))))

  case class SpanInlineGen(contents: RArr[XConInedit], attribs: RArr[HAtt]) extends SpanInlineInedit
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
  def display(contents: XConInedit*)(otherDisplay: CssDec*): SpanHtml = new SpanLineGen(contents.toArr, RArr())
  { override def attribs: RArr[HAtt] = super.attribs +% StyleAtt(otherDisplay.toArr)
  }

  /** Factory method for creating HTML span element with a Style attribute with a colour declaration. */
  def colour(colour: Colour, contents: XConInedit*): SpanHtml = new SpanLineGen(contents.toArr, RArr())
  { override def attribs: RArr[HAtt] = super.attribs +% StyleAtt(ColourDec(colour))
  }

  /** Factory method to create a span line with a class attribute. */
  def classAtt(classStr: String, conStr: String, otherAttribs: XAtt*): SpanLine = new SpanLineGen(RArr(conStr), ClassAtt(classStr) %: otherAttribs.toArr)

  /** Creates a Span line and registers the textContent with an HTML Text Input. */
  def listenText(input: UpdaterInputStr, otherAttribs: XAtt*)(f: String => String): SpanLine =
  { def newId = input.next1Text(f)
    SpanLineGen(RArr(f(input.valueStr)), newId %: otherAttribs.toRArr)
  }

  /** Creates a span line and registers the textContent with 2 HTML Text Inputs. */
  def listen2Text(input1: UpdaterStr, input2: UpdaterStr, otherAttribs: XAtt*)(f: (String, String) => String): SpanLine =
  { val newId: IdAtt = input1.next2Text1(input2, f)
    SpanLineGen(RArr(f(input1.valueStr, input2.valueStr)), newId %: otherAttribs.toRArr)
  }

  /** Creates a span line and registers the textContent with 3 HTML Text Inputs. */
  def listen3Text(input1: UpdaterStr, input2: UpdaterStr, input3: UpdaterStr, otherAttribs: XAtt*)(f: (String, String, String) => String): SpanLine =
  { val newId: IdAtt = input1.next3Id1(input2, input3, f)
    SpanLineGen(RArr(f(input1.valueStr, input2.valueStr, input3.valueStr)), newId %: otherAttribs.toRArr)
  }

  /** Creates a span line and registers the textContent with an HTML Text and a number of Inputs. */
  def listenTextNum(input1: UpdaterStr, input2: UpdaterDblInput, otherAttribs: XAtt*)(f: (String, Double) => String): SpanLine =
  { val newId: IdAtt = input1.nextStrDblId1(input2, f)
    SpanLineGen(RArr(f(input1.valueStr, input2.value)), newId %: otherAttribs.toRArr)
  }

  /** Creates a Bash line and registers the textContent with an HTML number Input. */
  def listenNum(input: UpdaterDblInput, otherAttribs: XAtt*)(f: Double => String): SpanLine =
  { def newId = input.next1(f)
    new SpanLineGen(RArr(f(input.value)), newId %: otherAttribs.toRArr)
  }

  /** HTML span element on its own line, with display set to block. */
  case class SpanLineGen(contents: RArr[XCon], otherAttribs: RArr[HAtt]) extends SpanLine
  { override def attribs: RArr[HAtt] = super.attribs ++ otherAttribs
  }
}