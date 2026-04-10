/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import wcode.*

/** An HTML page, contains a doctype, a head and a body elements. */
trait HtmlPage extends HttpContent
{ /** The HTML element of this [[HtmlPage]] consisting of an [[HeadHtml]] and an [[BodyHtml]]. */
  def htmlElem: HtmlHtml = HtmlHtml(head, body)

  /** The head of this [[HtmlPage]]. */
  def head: HeadHtml

  /** The body of this [[HtmlPage]]. */
  def body: BodyHtml

  override def out: String = "<!doctype html>" --- htmlElem.out(0, 150)

  def htmlEscape: HtmlCodePre = HtmlCodePre(out.escapeHtml)

  def zioOut: String = "\n" + htmlElem.out(0, 150)

  override def httpResp(dateStr: String, server: String): HttpRespBodied = HttpFound(dateStr, server, HttpConTypeHtml, out)
}

/** Companion object for the [[HeadHtml]] class. */
object HtmlPage
{ /** Factory apply method for [[HtmlPage]]. */
  def apply(head: HeadHtml, body: BodyHtml): HtmlPage = HtmlPageGen(head, body)

  /** A quick and crude method for creating an HTML page object from the title String and the HTML body contents String. */
  def titleOnly(title: String, bodyContent: String): HtmlPage = HtmlPageGen(HeadHtml.title(title), BodyHtml(HtmlH1(title), bodyContent))
  
  /** General implementation class for [[HtmlPage]]. */
  case class HtmlPageGen(head: HeadHtml, body: BodyHtml) extends HtmlPage
}

/** This is an HTML page that stores its default file name. */
trait HtmlPageFile extends HtmlPage, OutElemFile
{ /** The default file name stem for this HTML page. */
  def fileNameStem: String

  /** The HTML head title [[String]]. */
  def titleStr: String

  /** The default file name stem for this HTML page file. */
  override def fileName: String = fileNameStem + ".html"

  /** creates an HTML head element with [[HtmlTitle]], [[HtmlCssLink]], [[HtmlUtf8]], [[HtmlViewDevWidth]] plus the repeat parameter elements. */
  def headCss(cssFileStem: String, otherContents: XConCompound*): HeadHtml =
    new HeadHtml(RArr[XConCompound](HtmlTitle(titleStr), HtmlCssLink(cssFileStem), HtmlUtf8, HtmlViewDevWidth) ++ otherContents)

  /** creates an HTML head element with [[HtmlTitle]], [[HtmlCssLink]], [[FaviconSvgLink]], [[HtmlUtf8]], [[HtmlViewDevWidth]] plus the repeat parameter
   * elements. */
  def headFavCss(cssFileStem: String, otherContents: XConCompound*): HeadHtml =
      new HeadHtml(RArr[XConCompound](HtmlTitle(titleStr), HtmlCssLink(cssFileStem), HtmlUtf8, FaviconSvgLink, HtmlViewDevWidth) ++ otherContents)
      
  override def head: HeadHtml = HeadHtml.title(titleStr)
}

/** An index.html page. */
trait IndexPage extends HtmlPageFile
{ override def fileNameStem: String = "index"
}

/** An HTML page with an accumulator of [[InputUpdater]]s. */
trait HtmlPageUpdater extends HtmlPageFile
{ var inpAcc: RArr[HtmlInputLike] = RArr()
}

/** A 404 HTML page. */
trait HtmlPageNotFound extends HtmlPage
{ override def httpResp(dateStr: String, server: String): HttpPageNotFound = HttpPageNotFound(dateStr, server, HttpConTypeHtml, out)
}

/** A standard off the shelf 404 HTML page. */
case class HtmlPageNotFoundstd(NotFoundUrl: String) extends HtmlPageNotFound
{ override def head: HeadHtml = HeadHtml.title("Page not Found")
  override def body: BodyHtml = BodyHtml(HtmlH1("404" -- NotFoundUrl -- "not found on this server"))
}