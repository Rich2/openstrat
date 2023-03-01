/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
//package ostrat
//package pHtmlFac
//import pWeb._, StratPage._, Colour._

/*
object Rcom
{ def apply(): List[StratPage]  = List(IndexPage, WWIIPage, Y1783Page, BC305Page, DungeonPage, PlanetPage, BrowserTestPage, AboutPage, ZugPage,
    CivPage, DraughtsPage)
  
  def local: List[StratPage] =  List(PlayPage, RoadmapPage, BlackjackPage) ++ Rcom()
  def jsDeploy: List[StratJsPage] = Rcom().filter(_.isInstanceOf[StratJsPage]).asInstanceOf[List[StratJsPage]]
}

object AboutPage extends TextPage
{ def title: String = "About"   
  def menuLabel = "About"
  def partLink: String = "about"   
  
  def middle: Seq[XCon] =
    Seq(HtmlP("This site's intial aim is to allow me to create some code and share it with family, friends and" + "co-workers."))
}

object TileLessonPage extends TextPage
{ def title: String = menuLabel   
  def menuLabel = "Tile Lesson"
  def partLink: String = "TileLesson"
  override def middle: Seq[XCon] = Seq(HtmlH1(menuLabel), HtmlP("Some Square Tiles: "), HCanvas("Canv1"))
}

object BrowserTestPage extends StratJsPage
{ def title = "Browser Functionality Test Page"
  def menuLabel = "Browser Test"
  def partLink = "browsertest"
  override def middle: Seq[XCon] = Seq(HtmlP("This page will test out your browser"), canv)
  override def scriptCalls(stage: Boolean): Seq[XCon] = srcScript(stage)
  override def jsFile: String = "browsertest"
  override def mainFile: String = "BrowsertestJsApp"
  override def jsVersion: String = "03"
}

object PlanetPage extends StratJsPage
{ def title: String = "Planets"   
  def menuLabel = "Planets"
  def partLink: String = "planets"
  override def middle: Seq[XCon] = Seq(canv)
  override def scriptCalls(stage: Boolean): Seq[XCon] = srcScript(stage)
  override def jsFile: String = "planets"
  override def mainFile: String = "PlanetsJsApp"   
  override def jsVersion: String = "02"
}

object WWIIPage extends StratJsPage
{ def title: String = "World War II game a work in progress"   
  def menuLabel = "WW2"
  def partLink: String = "ww2"
  override def middle: Seq[XCon] = Seq(canv)
  override def scriptCalls(stage: Boolean): Seq[XCon] = srcScript(stage)
  override def jsFile: String = "ww2"
  override def mainFile: String = "Ww2JsApp"  
  override def jsVersion: String = "01"   
}

object Y1783Page extends StratJsPage
{ def title: String = "1783 game a work in progress"   
  def menuLabel = "Y1783"
  def partLink: String = "y1783"
  override def middle: Seq[XCon] = Seq(canv)
  override def scriptCalls(stage: Boolean): Seq[XCon] = srcScript(stage)
  override def jsFile: String = "y1783"
  override def mainFile: String = "Y1783JsApp"  
  override def jsVersion: String = "02"   
}

object DraughtsPage extends StratJsPage
{ def title: String = "Draughts and Checkers a work in progress"   
  def menuLabel = "Draughts"
  def partLink: String = "draughts"
  override def middle: Seq[XCon] = Seq(canv)
  override def scriptCalls(stage: Boolean): Seq[XCon] = srcScript(stage)
  override def jsFile: String = "draughts"
  override def mainFile: String = "DraughtsJsApp"  
  override def jsVersion: String = "01"   
}

object PlayPage extends StratJsPage
{ def title: String = "A testing App"   
  def menuLabel = "Play"
  def partLink: String = "play"
  override def middle: Seq[XCon] = Seq(canv, HtmlP("A testing app."))
  override def scriptCalls(stage: Boolean): Seq[XCon] = srcScript(stage)
  override def jsFile: String = "play"
  override def mainFile: String = "PlaysJsApp"  
  override def jsVersion: String = "01"   
}

object BC305Page extends StratJsPage
{ def title: String = "BC305 game a work in progress"   
  def menuLabel = "BC305"
  def partLink: String = "bc305"
  override def middle: Seq[XCon] = Seq(canv)
  override def scriptCalls(stage: Boolean): Seq[XCon] = srcScript(stage)
  override def jsFile: String = "bc305"
  override def mainFile: String = "BC305JsApp"  
  override def jsVersion: String = "01"   
}

object DungeonPage extends StratJsPage
{ def title: String = "Dungeon Game"   
  def menuLabel = "Dungeon Game"
  def partLink: String = "dungeon"
  override def middle: Seq[XCon] = Seq(canv)
  override def scriptCalls(stage: Boolean): Seq[XCon] = srcScript(stage) 
  override def jsFile: String = "dungeon"
  override def mainFile: String = "DungeonJsApp"
  override def jsVersion: String = "02"
}

object ZugPage extends StratJsPage
{ def title: String = menuLabel   
  def menuLabel = "Zugfuhrer"
  def partLink: String = "zug"
  override def middle: Seq[XCon] = Seq(canv)
  override def scriptCalls(stage: Boolean): Seq[XCon] = srcScript(stage)
  override def jsFile: String = "zug"
  override def mainFile: String = "ZugJsApp"
  override def jsVersion: String = "01"
}

object CivPage extends StratJsPage
{ def title: String = "Rise of Civs"   
  def menuLabel = "Civ Rise"
  def partLink: String = "civ"
  override def middle: Seq[XCon] = Seq(canv)
  override def scriptCalls(stage: Boolean): Seq[XCon] = srcScript(stage)
  override def jsFile: String = "civ"
  override def mainFile: String = "CivJsApp"
  override def jsVersion: String = "01"
}

object BlackjackPage extends StratJsPage
{ def title: String = "Blackjack"   
  def menuLabel = "Blackjack"
  def partLink: String = "blackjack"  
  override def middle: Seq[XCon] = Seq(canv)
  override def scriptCalls(stage: Boolean): Seq[XCon] = srcScript(stage)
  override def jsFile: String = "blackjack"
  override def mainFile: String = "BlackjackJsApp"
  override def jsVersion: String = "03"   
}

object RoadmapPage extends TextPage
{ def title: String = "Roadmap"   
  def menuLabel = "Roadmap"
  def partLink: String = "roadmap"
  override def middle: Seq[XCon] = Seq(HtmlH1("Roadmap")) :+ lists
  def roadList(strs: Seq[String], colour: Colour) = HUList(strs.map(i => new HLItem(i)): _*)(StyleAtt(CssColour(colour)))
   
  def lists: XCon =
  { import scala.io.Source
    
    val gs = Source.fromFile(resDir / "toDo.txt").getLines().foldLeft((Seq[Seq[String]](), Seq[String]()))((acc, line) => line match
    { case "" => acc        
      case l if l.head == '#' => (acc._1 :+ acc._2, Seq())
      case l if l.length < 2 => acc
      case l if l.tail.head == '#' => (acc._1 :+ acc._2, Seq())
      case l if l.forall(_.isWhitespace) => acc
      case l => (acc._1, acc._2 :+ l)
     })
    
    if (gs._1.length != 2) throw new Exception((gs._1.length + 1).toString -- "lists. Should be 3 lists in ToDo.txt")

    val urgentIssues = toDoEmp(gs._1(0), "Urgent Issues", Red)
    val futureTasks = toDoEmp(gs._1(1), "Future Tasks", DarkOrange)
    val completed = toDoEmp(gs._2, "Completed", Green)
    HtmlDiv((urgentIssues ++ futureTasks ++ completed): _*)(StyleAtt(CssMaxWidth("1100px"), CssMarginAuto))
  }
   
  def toDoEmp(items: Seq[String], title: String, colour: Colour): Seq[HtmlEl] = items.ifEmpty(Seq (HtmlH3(title :- "None")), Seq(HtmlH3(title),
    roadList(items, colour)))   
}

object UtilitiesPage extends TextPage
{ def title: String = "My utilites package"   
  def menuLabel = "My Scala Utilites"
  def partLink: String = "utilities"
  override def middle: Seq[XCon] = Seq(introText)
 
  def introText = HtmlP.r(
    "I've included my utilites package so as I can blog about Scala code. This will allow me to publish mycode as is, without having to dig out utiity",
    "classes, objects and methods, which I use for convenience. Its a very much a work in progress and has a lot of rough edges. So with that warning",
    "here is:" , HtmlBr,
    "<a href=", "/api/index.html".enquote, ">Scala Utility package</a>")()
}*/