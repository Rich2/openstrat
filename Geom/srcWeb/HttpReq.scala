/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An HTTP Request. */
class HttpReq(val method: HttpMethod, val uri: String)
{ /** HTTP method such as GET or POST. */
  
}

object HttpReq
{ /** Apply method, finds [[HttpReq]] from [[Strng]]s. */
  def apply(lines: Sequ[String]): ThrowMon[HttpReq] =
  { val lh0 = lines(0)
    debvar(lh0)
    val rt = lh0.takeWhile(_.isLetter)
    val tail = lh0.drop(rt.length).dropWhile(_.isWhitespace)
    if (rt.toLowerCase == "get") Succ(new HttpReq(HttpGet, tail.takeWhile(c => !c.isWhitespace)))
    else
      if (rt.toLowerCase == "post") Succ(new HttpReq(HttpPost, tail.takeWhile(c => !c.isWhitespace)))
      else
      { debvar(rt)
        FailExc("Not get")
      }
  }
}

/** HTTP Get Request extractor object. */
object HttpReqGet
{ //override def unapply: HttpMethod = HttpGet
}

/** HTTP Post Request extractor object. */
object HttpReqPost
{ //override def method: HttpMethod = HttpPost
}

/** HttP method type. */
sealed trait HttpMethod

/** Http Get method type. */
object HttpGet extends HttpMethod

/** Http Post method type. */
object HttpPost extends HttpMethod