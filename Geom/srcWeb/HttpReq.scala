/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An HTTP Request. */
trait HttpReq
{ /** HTTP method such as GET or POST. */
  def method: HttpMethod
}

object HttpReq
{ /** Apply method, finds [[HttpReq]] from [[Strng]]s. */
  def apply(lines: Sequ[String]): EMonOld[HttpReq] =
  { val lh0 = lines(0)
    debvar(lh0)
    val rt = lh0.takeWhile(_.isLetter)
    val tail = lh0.drop(rt.length).dropWhile(_.isWhitespace)
    if (rt.toLowerCase == "get") Good(HttpReqGet(tail.takeWhile(c => !c.isWhitespace)))
    else
      if (rt.toLowerCase == "post") Good(HttpReqPost(tail.takeWhile(c => !c.isWhitespace)))
      else
      { debvar(rt)
        badNone("Not get")
      }
  }
}

/** HTTP Get Request. */
class HttpReqGet(val uri: String) extends HttpReq
{ override def method: HttpMethod = HttpGet
}

/** HTTP Post Request. */
class HttpReqPost(val uri: String) extends HttpReq
{ override def method: HttpMethod = HttpPost
}

/** HttP method type. */
sealed trait HttpMethod

/** Http Get method type. */
object HttpGet extends HttpMethod

/** Http Post method type. */
object HttpPost extends HttpMethod