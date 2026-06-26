/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb;

trait HtmlElemCompanion[T]
{
  def apply(contents: RArr[XCon], attribs: RArr[XAtt]): T
}

case class HtmlElemBuilder(contents: RArr[XCon], attribs: RArr[XAtt])
{
  def apply[A](builder: HtmlElemCompanion[A]): A = builder.apply(contents, attribs)
}

object HtmlElemBuilder
{
  /** Creates a Bash line and registers the textContent with an HTML Select Input and an HTML number input. */
  def listenOptIntHtml(input1: UpdaterOption, input2: UpdaterIntInput)(f: (OptionHtml, Int) => RArr[XCon]): HtmlElemBuilder =
  { val newId: IdAtt = input1.nextOptInt1Html(input2, f)
    HtmlElemBuilder(f(input1.initOption, input2.value), RArr(newId))
  }
}