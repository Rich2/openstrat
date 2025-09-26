/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An HTML page, contains a doctype, a head and a body elements. */
trait HtmlPage extends HttpContent
{ /** The HTML element of this [[HtmlPage]] consisting of an [[HtmlHead]] and an [[HtmlBody]]. */
  def htmlElem: HtmlHtml = HtmlHtml(head, body)

  /** The head of this [[HtmlPage]]. */
  def head: HtmlHead

  /** The body of this [[HtmlPage]]. */
  def body: HtmlBody

  override def out: String = "<!doctype html>" --- htmlElem.out(0, 150)
  def zioOut: String = "\n" + htmlElem.out(0, 150)

  override def httpResp(dateStr: String, server: String): HttpRespBodied = HttpFound(dateStr, server, HttpConTypeHtml, out)
}

/** Companion object for the [[HtmlHead]] class. */
object HtmlPage
{ /** Factory apply method for [[HtmlPage]]. */
  def apply(headIn: HtmlHead, bodyIn: HtmlBody): HtmlPage = new HtmlPage
  { override val head: HtmlHead = headIn
    override val body: HtmlBody = bodyIn
  }

  /** A quick and crude method for creating an HTML page object from the title String and the HTML body contents String. */
  def titleOnly(title: String, bodyContent: String): HtmlPage = new HtmlPage{
    override val head: HtmlHead = HtmlHead.title(title)
    override val body: HtmlBody = HtmlBody(HtmlH1(title), bodyContent)
  }
}

trait HtmlPageInput extends HtmlPage
{
  var inpTextAcc: RArr[InputUpdaterText] = RArr()
  var inpNumAcc: RArr[InputUpdaterNum] = RArr()
}

/** A 404 HTML page. */
trait HtmlPageNotFound extends HtmlPage
{ override def httpResp(dateStr: String, server: String): HttpPageNotFound = HttpPageNotFound(dateStr, server, HttpConTypeHtml, out)
}

/** A standard off the shelf 404 HTML page. */
case class HtmlPageNotFoundstd(NotFoundUrl: String) extends HtmlPageNotFound
{ override def head: HtmlHead = HtmlHead.title("Page not Found")
  override def body: HtmlBody = HtmlBody(HtmlH1("404" -- NotFoundUrl -- "not found on this server"))
}

/** HTML title element. */
case class HtmlTitle(str: String, attribs: RArr[XAtt] = RArr()) extends HtmlUnvoid
{ override def tag = "title"
  override def contents: RArr[XCon] = RArr(str)
  override def out(indent: Int, line1InputLen: Int = 0, maxLineLen: Int = 150): String = indent.spaces + "<title>" + str + "</title>"
}

/** The "html" HTML element */
case class HtmlHtml(head: HtmlHead, body: HtmlBody, attribs: RArr[XAtt] = RArr()) extends HtmlUnvoid
{ def tag: String = "html"
  override def contents: RArr[HtmlUnvoid] = RArr(head, body)
  def out(indent: Int, line1InputLen: Int = 0, maxLineLen: Int = 150): String =
    openTag2(indent, line1InputLen, maxLineLen) + head.out() + "\n\n" + body.out(0) + n2CloseTag
}