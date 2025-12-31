/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import wcode.*

/** An HTML page, contains a doctype, a head and a body elements. */
trait HtmlPage extends HttpContent
{ /** The HTML element of this [[HtmlPage]] consisting of an [[HtmlHead]] and an [[HtmlBody]]. */
  def htmlElem: HtmlHtml = HtmlHtml(head, body)

  /** The head of this [[HtmlPage]]. */
  def head: HtmlHead

  /** The body of this [[HtmlPage]]. */
  def body: HtmlBody

  override def out: String = "<!doctype html>" --- htmlElem.out(0, 150)

  def htmlEscape: HtmlCodePre = HtmlCodePre(out.escapeHtml)

  def zioOut: String = "\n" + htmlElem.out(0, 150)

  override def httpResp(dateStr: String, server: String): HttpRespBodied = HttpFound(dateStr, server, HttpConTypeHtml, out)
}

/** Companion object for the [[HtmlHead]] class. */
object HtmlPage
{ /** Factory apply method for [[HtmlPage]]. */
  def apply(head: HtmlHead, body: HtmlBody): HtmlPage = HtmlPageGen(head, body)

  /** A quick and crude method for creating an HTML page object from the title String and the HTML body contents String. */
  def titleOnly(title: String, bodyContent: String): HtmlPage = HtmlPageGen(HtmlHead.title(title), HtmlBody(HtmlH1(title), bodyContent))
  
  /** General implementation class for [[HtmlPage]]. */
  case class HtmlPageGen(head: HtmlHead, body: HtmlBody) extends HtmlPage
}

/** This is an HTML page that stores its default file name. */
trait HtmlPageFile extends HtmlPage
{ /** The default file name stem for this HTML page. */
  def fileNameStem: String

  /** The HTML head title [[String]]. */
  def titleStr: String

  /** The default file name stem for this HTML page. */
  def fileName: String = fileNameStem + ".html"

  /** creates an HTML head element with [[HtmlTitle]], [[HtmlCssLink]], [[HtmlUtf8]], [[HtmlViewDevWidth]] plus the repeat parameter elements. */
  def headCss(cssFileStem: String, otherContents: XConCompound*): HtmlHead =
    new HtmlHead(RArr[XConCompound](HtmlTitle(titleStr), HtmlCssLink(cssFileStem), HtmlUtf8, HtmlViewDevWidth) ++ otherContents)

  /** creates an HTML head element with [[HtmlTitle]], [[HtmlCssLink]], [[FaviconSvgLink]], [[HtmlUtf8]], [[HtmlViewDevWidth]] plus the repeat parameter
   * elements. */
  def headFavCss(cssFileStem: String, otherContents: XConCompound*): HtmlHead =
      new HtmlHead(RArr[XConCompound](HtmlTitle(titleStr), HtmlCssLink(cssFileStem), HtmlUtf8, FaviconSvgLink, HtmlViewDevWidth) ++ otherContents)
}

/** An index.html page. */
trait IndexPage extends HtmlPageFile
{ override def fileNameStem: String = "index"
}

/** An HTML page with an accumulator of [[InputUpdater]]s. */
trait HtmlPageInput extends HtmlPageFile
{ var inpAcc: RArr[InputUpdater] = RArr()
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