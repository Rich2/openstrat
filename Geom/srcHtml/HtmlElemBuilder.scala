/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb;

trait HtmlElemCompanion[T]
{ /** Utility method to allow many other factory methods to implemented in this super trait. */
  def fromStr(str: String, attribs: RArr[XAtt]): T
  
  /** Creates an HTML element of the given type and registers the textContent with a String => String callback to the textContent. */
  def listenStr(input: UpdaterStr, otherAttribs: RArr[XAtt] = RArr())(f: String => String): T =
  { val newId: IdAtt = input.next1Text(f)
    fromStr(f(input.valueStr), newId %: otherAttribs)
  }
}

trait HtmlElemFullCompanion[T] extends HtmlElemCompanion[T]
{
  def apply(contents: RArr[XCon], attribs: RArr[XAtt]): T

  /** Creates an HTML element of the given type and listens to an [[UpdaterOption]] change events modifying the inner HTML. */
  def listenOptHtml(input: UpdaterOption, otherAttribs: RArr[XAtt] = RArr())(f: OptionHtml => RArr[XCon]): T =
  { val newId: IdAtt = input.nextOptHtml(f)
    apply(input.listenerInit(f), newId %: otherAttribs)
  }
}

case class HtmlElemBuilder(contents: RArr[XCon], attribs: RArr[XAtt])
{
  def apply[A](builder: HtmlElemFullCompanion[A]): A = builder.apply(contents, attribs)
}

object HtmlElemBuilder
{
  /** Creates a Bash line and registers the textContent with an HTML Select Input and an HTML number input. */
  def listenOptIntHtml(input1: UpdaterOption, input2: UpdaterIntInput)(f: (OptionHtml, Int) => RArr[XCon]): HtmlElemBuilder =
  { val newId: IdAtt = input1.nextOptInt1Html(input2, f)
    HtmlElemBuilder(f(input1.initOption, input2.value), RArr(newId))
  }
}