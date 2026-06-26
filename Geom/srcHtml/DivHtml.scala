/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** HTML Div element.  */
trait DivHtml extends HtmlUnvoid
{ override def tagName: String = "div"
}

/** Companion object for the [[DivHtml]] DIV element class, contains various factory methods. */
object DivHtml
{ /** Factory apply method for div HTML element. There is an apply overload that takes an [[RArr]] of [[XConInedit]] and an [[RArr]] of [[XAtt]], with a default of no
 * [[XAtt]]s. */
  def apply(input: XCon*): DivHtml = new DivHtmlGen(input.toRArr, RArr())

  /** Factory apply method for div HTML element. */
  def apply(contents: RArr[XCon], attribs: RArr[XAtt] = RArr()): DivHtml = new DivHtmlGen(contents, attribs)

  /** Factory method to create Div element with an ID attribute. */
  def id(id: String, contents: XCon*): DivHtml = new DivHtmlGen(contents.toArr, RArr(IdAtt(id)))

  /** Factory method to create Div element with a class attribute. */
  def classAtt(id: String, contents: XCon*): DivHtml = new DivHtmlGen(contents.toArr, RArr(ClassAtt(id)))

  /** Factory method to create Div element with a class attribute. */
  def classAtt(id: String, contents: RArr[XCon], otherAtts: RArr[XAtt] = RArr()): DivHtml = new DivHtmlGen(contents, ClassAtt(id) %: otherAtts)

  /** Factory apply method for creating HTML span element with a display attribute. */
  def display(contents: XConInedit*)(otherDisplay: CssDec*) = new DivHtmlGen(contents.toArr, RArr(StyleAtt(otherDisplay.toArr)))

  /** Creates a Div and listens to an [[UpdaterStr]] change events modifying the textContent. */
  def listenStrText(input: UpdaterStr)(f: String => String): DivHtml =
  { def newId = input.next1Text(f)
    new DivHtmlGen(RArr(f(input.valueStr)), RArr(newId))
  }

  /** Creates a Div and registers with a [[UpdaterStr]]. Changes inner HTML on change event. */
  def listenStrHtml(input: UpdaterStr)(f: String => RArr[XCon]): DivHtml =
  { def newId = input.next1Html(f)
    new DivHtmlGen((f(input.valueStr)), RArr(newId))
  }

  /** Creates an HTML div element and Listens to [[UpdaterDblInput]] change events and modifies the HTML textContent. */
  def listenDbl(input: UpdaterDblInput)(f: Double => String): DivHtml =
  { val newId: IdAtt = input.next1(f)
    new DivHtmlGen(RArr(f(input.value)), RArr(newId))
  }

  /** Creates a Div and listens to an [[UpdaterOption]] change events modifying the  inner HTML. */
  def listenOptHtml(input: UpdaterOption)(f: OptionHtml => RArr[XCon]): DivHtml =
  { val newId: IdAtt = input.nextOptHtml(f)
    new DivHtmlGen(input.listenerInit(f), RArr(newId))
  }

  /** Creates a Bash line and registers the textContent with an HTML Select Input and an HTML number input. */
  def listenOptIntHtml(input1: UpdaterOption, input2: UpdaterIntInput)(f: (OptionHtml, Int) => RArr[XCon]): DivHtml ={
    val newId: IdAtt = input1.nextOptInt1Html(input2, f)
    new DivHtmlGen(f(input1.initOption, input2.value), RArr(newId))
  }

  /** Creates a Bash line and registers the textContent with an HTML Select Input and an HTML number input. */
  def listenOptDblHtml(input1: UpdaterOption, input2: UpdaterDblInput)(f: (OptionHtml, Double) => RArr[XCon]): DivHtml =
  { val newId: IdAtt = input1.nextOptDbl1Html(input2, f)
    new DivHtmlGen(f(input1.initOption, input2.value), RArr(newId))
  }

  /** An implementation class for the general case of an HTML Div.  */
  class DivHtmlGen(val contents: RArr[XCon], val attribs: RArr[XAtt]) extends DivHtml, HtmlOwnLine
}

/** HTML Div that requires its own liine in the editor. */
trait DivLine extends DivHtml, HtmlOwnLine

/** Class for creating HTML Div element with a Style attribute with a colour declaration. */
class DivColour(colour: Colour, val contents: RArr[XCon], val otherAttribs: RArr[XAtt]) extends DivLine
{ override def attribs: RArr[XAtt] = StyleAtt(ColourDec(colour)) %: otherAttribs
}

object DivColour
{/** Factory method for creating an HTML Div element with a Style attribute with a colour declaration. */
  def apply(colour: Colour, contents: XCon*): DivColour = new DivColour(colour, contents.toRArr, RArr())
}