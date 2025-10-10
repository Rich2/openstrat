/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

trait OutElem
{ /** The output [[String]]. Includes HTML and CSS code. */
  def out: String
}

/** Http content that can be sent as a body of an Http response. */
trait HttpContent extends OutElem
{ /** Create an [[HtmlResp]] response with this HTML as its body. */
  def httpResp(dateStr: String, server: String): HttpRespBodied

  /** Create an [[HtmlResp]] response with this HTML as its body in bytes. */
  def httpRespBytes(dateStr: String, server: String): Array[Byte] = httpResp(dateStr, server).out.getBytes
}

/** HTTP content type. */
sealed trait HttpContentType extends HttpHeader
{ def str1: String
  def str2: String
  def valueStr = str1 / str2
  //def out: String = str1 / str2

  override def name: String = "Content"
}

trait HttpContentTypeApp extends HttpContentType
{ override def str1: String = "application"
}

trait HttpContentTypeText extends HttpContentType
{ override def str1: String = "text"
}

object HttpConTypeHtml extends HttpContentTypeText
{ override def str2: String = "html"
}

object HttpConTypeCss extends HttpContentTypeText
{ override def str2: String = "css"
}

object HttpConTypeJs extends HttpContentTypeText
{ override def str2: String = "javascript"
}

object HttpConTypePlain extends HttpContentTypeText
{ override def str2: String = "plain"
}

trait HttpContentTypeImage extends HttpContentType
{ override def str1: String = "image"
}

object HttpConTypeSvg extends HttpContentTypeImage
{ override def str2: String = "svg+xml"
}