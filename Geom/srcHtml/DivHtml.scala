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

  /** Factory apply method for creating HTML span element with a display attribute. */
  def display(contents: XConInedit*)(otherDisplay: CssDec*) = new DivHtmlGen(contents.toArr, RArr(StyleAtt(otherDisplay.toArr)))

  /** Factory method for creating HTML Div element with a Style attribute with a colour declaration. */
  def colour(colour: Colour, contents: XConInedit*): DivHtml = new DivHtmlGen(contents.toArr, RArr(StyleAtt(ColourDec(colour))))

  /** Creates a Div and listens to an [[UpdaterText]] change events modifying the textContent. */
  def listenStrCon(input: UpdaterText)(f: String => String): DivHtml =
  { def newId = input.next1Id(f)
    new DivHtmlGen(RArr(f(input.valueStr)), RArr(newId))
  }

  /** Creates a Div and registers with a [[UpdaterText]]. Changes inner HTML on change event. */
  def listenStrHtml(input: UpdaterText)(f: String => RArr[XCon]): DivHtml =
  { def newId = input.nextHtmlId(f)
    new DivHtmlGen((f(input.valueStr)), RArr(newId))
  }

  /** Creates an HTML div element and Listens to [[InputUpdaterNum]] change events and modifies the HTML textContent. */
  def listenNum(input: InputUpdaterNum)(f: Double => String): DivHtml =
  { val newId: IdAtt = input.next1Id(f)
    new DivHtmlGen(RArr(f(input.value)), RArr(newId))
  }

  /** An implementation class for the general case of an HTML Div.  */
  class DivHtmlGen(val contents: RArr[XCon], val attribs: RArr[XAtt]) extends DivHtml, HtmlOwnLine
}

trait DivLine extends DivHtml, HtmlOwnLine