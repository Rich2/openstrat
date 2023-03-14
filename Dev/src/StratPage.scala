/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
/*package ostrat
package pHtmlFac
import pWeb._*/

/*object StratPage
{
  val cssVersion: String = "07"  
  def projDir = "/GenProj"
  def resDir = projDir / "HtmlRes"
  def stageDir = projDir / "staging"
  def fastPagesDir = projDir / "fastPages"
  def onlyCss: String = resDir / "only.css"
  def cssVersionName = "only" + cssVersion + ".css"
  def htmlRequirements: HtmlDiv = HtmlDiv()(IdAtt("sjs"))
}

import StratPage._
sealed trait StratPage
{   
  def out(stage: Boolean): String = HtmlPage.get(headMems, bodyMems(stage))
  def title: String   
  def headMems: Seq[HtmlEl] = List(TabTitle("RichStrat:" -- title), HtmlUTF8, HMetaNC("viewport", "width=device-width"), 
      CssLink("only" + cssVersion))   
  def menuLabel: String
  def partLink: String
  def fullLink = partLink + ".html"
  def getItem = new HLinkItem(fullLink, menuLabel) 
  def bottom(stage: Boolean): Seq[XCon] = Seq(
      HtmlDiv("For the time being please put any comments about the site on the gitter channel:", gitterLink)(),
      menu("bottommenu", stage), HtmlDiv()(StyleAtt(CssFlex("1"))), 
      HtmlDiv()(IdAtt("footer")))
  def menu(menuStr: String, stage: Boolean) = HUList(ife(stage, Rcom(), Rcom.local).map(_.getItem) : _*)(IdAtt(menuStr))
  def topMenu(stage: Boolean) = Seq(menu("topmenu", stage))
  def gitterLink = HLinkItem("https://gitter.im/typestrat/Lobby", "openstrat-gitter") 
  def bodyMems(stage: Boolean): Seq[XCon]       
}

trait TextPage extends StratPage
{
  def middle: Seq[XCon]
  override def bodyMems(stage: Boolean): Seq[XCon] = topMenu(stage) ++ middle ++ bottom(stage)       
}

trait StratJsPage extends StratPage
{
  def middle: Seq[XCon]
  def scriptCalls(stage: Boolean): Seq[XCon]
  override def bodyMems(stage: Boolean): Seq[XCon] = topMenu(stage) ++ middle ++ scriptCalls(stage)
  def jsFile: String
  def mainFile: String
  def jsVersion: String
  
  def srcScript(stage: Boolean): Seq[XCon] =
  { val jsMod: String = if (stage) stageName else fastName   
    val j1 = HtmlJs()(HAttJs, XAtt("src", "./" + jsMod)) 
    val ht = IndText(mainFile + ".main();")
    val j2 = HtmlJs(ht)(HAttJs)      
    Seq(j1, j2, HNoScript)      
  }
  
  def stageName: String = jsFile + jsVersion + ".js"
  def fastName: String = jsFile + "-opt.js"
  def canv = HCanvas("scanv")
  //def sdiv : HtmlDiv =  HtmlDiv()(IdAtt("sjs"))  
}*/