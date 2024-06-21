/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** HTTP content type. */
sealed trait HttpContentType
{ def str1: String
  def str2: String
  def out: String = str1 / str2
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