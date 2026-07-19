/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** An HTTP Request. */
class HttpReq(val method: HttpMethod, val uri: String)

object HttpReq
{ /** Apply method, finds [[HttpReq]] from [[String]]s. */
  def apply(lines: Sequ[String]): ThrowMon[HttpReq] =
  { val lh0 = lines(0)
    debvar(lh0)
    val rt = lh0.takeWhile(_.isLetter)
    val tail = lh0.drop(rt.length).dropWhile(_.isWhitespace)
    if (rt.toLowerCase == "get") Succ(new HttpReq(GetHttp, tail.takeWhile(c => !c.isWhitespace)))
    else
      if (rt.toLowerCase == "post") Succ(new HttpReq(PostHttp, tail.takeWhile(c => !c.isWhitespace)))
      else
      { debvar(rt)
        FailExc("Not get")
      }
  }
}

/** HTTP method type. */
sealed trait HttpMethod extends CssVal

/** HTTP Get method type. */
object GetHttp extends HttpMethod
{ override def str: String = "get"
}

/** HTTP Post method type. */
object PostHttp extends HttpMethod
{ override def str: String = "post"
}

/** HTTP Head method type. */
object HeadHttp extends HttpMethod
{ override def str: String = "post"
}

/** HTTP method type attribute. */
sealed class MethodAtt(val method: HttpMethod) extends XAttShort
{ override def name: String = "method"
  override def valueStr: String = method.str  
}

/** HTTP Get method type attribute. */
case object GetAtt extends MethodAtt(GetHttp)

/** HTTP Post method type attribute. */
case object PostAtt extends MethodAtt(PostHttp)

/** HTTP Head method type attribute. */
case object HeadHttpAtt extends MethodAtt(HeadHttp)