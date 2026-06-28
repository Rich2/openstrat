/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** HTML element. */
trait HtmlElem extends XHmlElem

/** The "html" HTML element */
case class HtmlHtml(head: HeadHtml, body: BodyHtml, attribs: RArr[XAtt] = RArr()) extends HtmlTagLines, HtmlUnvoid
{ def tagName: String = "html"
  override def contents: RArr[HtmlUnvoid] = RArr(head, body)

  override def out(indent: Int, line1InputLen: Int = 0, maxLineLen: Int = 150): String =
    openTag1(indent, line1InputLen, maxLineLen) + head.out() + "\n\n" + body.out(0) + n1CloseTag
}

/** An HTML element that is not void. */
trait HtmlUnvoid extends HtmlElem
{ def openTag1(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String = openTag(indent, line1InputLen, maxLineLen) + "\n"
  def openTag2(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String = openTag(indent, line1InputLen, maxLineLen) + "\n\n"

  /** The full length of the opening tag without attributes. */
  def closeTagMinLen: Int = tagName.length + 3
}

/** An HTML element that can be inlined. */
trait HtmlInedit extends HtmlElem, XHmlInedit

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