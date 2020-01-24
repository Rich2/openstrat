package ostrat
package pHtmlFac
import pWeb._, StratPage._

object IndexPage extends TextPage
{
   def title: String = "Home"   
   def menuLabel = "Home"
   def partLink: String = "index"
   val intro: HtmlFile = HtmlFile(resDir / "IndexIntro.txt")
   override def middle: Seq[XCon] = List(HtmlDiv(intro)(),
   )
}
