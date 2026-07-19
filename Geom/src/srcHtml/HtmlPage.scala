/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
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

  def htmlEscape: PreCode = PreCode(out.escapeHtml)

  def zioOut: String = "\n" + htmlElem.out(0, 150)

  override def httpResp(dateStr: String, server: String): HttpRespBodied = HttpFound(dateStr, server, HttpConTypeHtml, out)
}

/** Companion object for the [[HeadHtml]] class. */
object HtmlPage
{ /** Factory apply method for [[HtmlPage]]. */
  def apply(head: HeadHtml, body: BodyHtml): HtmlPage = HtmlPageGen(head, body)

  /** A quick and crude method for creating an HTML page object from the title String and the HTML body contents String. */
  def titleOnly(title: String, bodyContent: String): HtmlPage = HtmlPageGen(HeadHtml.title(title), BodyHtml(H1Html(title), bodyContent))
  
  /** General implementation class for [[HtmlPage]]. */
  case class HtmlPageGen(head: HeadHtml, body: BodyHtml) extends HtmlPage
}

/** This is an HTML page that stores its default file name. */
trait HtmlPageFile extends HtmlPage, OutElemFileExt
{ /** The HTML head title [[String]]. */
  def titleStr: String

  override def fileExtStr: String = "html"
  override def fileName: HtmlFileName = HtmlFileName(fileStemStr)

  /** creates an HTML head element with [[TitleHtml]], [[CssLink]], [[HtmlUtf8]], [[HtmlViewDevWidth]] plus the repeat parameter elements. */
  def headCss(cssFileStem: String, otherContents: XConCompound*): HeadHtml =
    new HeadHtml(RArr[XConCompound](TitleHtml(titleStr), CssLink(cssFileStem), HtmlUtf8, HtmlViewDevWidth) ++ otherContents)

  /** creates an HTML head element with [[TitleHtml]], [[CssLink]], [[FaviconSvgLink]], [[HtmlUtf8]], [[HtmlViewDevWidth]] plus the repeat parameter
   * elements. */
  def headFavCss(cssFileStem: String, otherContents: XConCompound*): HeadHtml =
      new HeadHtml(RArr[XConCompound](TitleHtml(titleStr), CssLink(cssFileStem), HtmlUtf8, FaviconSvgLink, HtmlViewDevWidth) ++ otherContents)
      
  override def head: HeadHtml = HeadHtml.title(titleStr)
}

/** An index.html page. */
trait IndexPage extends HtmlPageFile
{ override def fileStemStr: String = "index"
}

/** A 404 HTML page. */
trait HtmlPageNotFound extends HtmlPage
{ override def httpResp(dateStr: String, server: String): HttpPageNotFound = HttpPageNotFound(dateStr, server, HttpConTypeHtml, out)
}

/** A standard off the shelf 404 HTML page. */
case class HtmlPageNotFoundstd(NotFoundUrl: String) extends HtmlPageNotFound
{ override def head: HeadHtml = HeadHtml.title("Page not Found")
  override def body: BodyHtml = BodyHtml(H1Html("404" -- NotFoundUrl -- "not found on this server"))
}