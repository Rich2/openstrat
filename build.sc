// build.sc
import mill._, scalalib._, scalajslib._

trait Common extends ScalaModule {
  def scalaVersion = "2.12.7"
  def scalacOptions = Seq("-feature", "-language:implicitConversions", "-deprecation", "-target:jvm-1.8", "-encoding", "UTF-8", "-unchecked", "-Xfuture", "-Xlint", "-Yno-adapted-args")
}

trait CommonJs extends ScalaJSModule with Common
{
	def scalaJSVersion = "0.6.25"
} 

object Macros extends Common
{  
  def ivyDeps = Agg(ivy"${scalaOrganization()}:scala-reflect:${scalaVersion()}")
}

object MacrosJs extends CommonJs
{  
	def sources = T.sources( Macros.millSourcePath / 'src)
	def ivyDeps = Agg(ivy"${scalaOrganization()}:scala-reflect:${scalaVersion()}")
}

object Core extends Common
{  
  def moduleDeps = Seq(Macros)
  object test extends Tests
  {
    def ivyDeps = Agg(ivy"com.lihaoyi::utest:0.6.5")
    def testFrameworks = Seq("utest.runner.Framework")    
  }  
}

object CoreJs extends CommonJs
{
  def moduleDeps = Seq(MacrosJs)
  def sources = T.sources( Core.millSourcePath / 'src)	
}

object FxStrat extends Common
{ def ivyDeps = Agg(ivy"org.scalafx::scalafx:8.0.144-R12")
  def moduleDeps = Seq(Core)
  def mainClass = Some("ostrat.pFx.DevApp") 
  import mill.modules.Assembly._
  def scalaLibraryIvyDeps = T { Agg.empty }   
}

object JsStrat extends CommonJs
{
  def ivyDeps = Agg(ivy"org.scala-js::scalajs-dom_sjs0.6:0.9.6")
  def moduleDeps = Seq(CoreJs)
  def sources = T.sources( millSourcePath / 'src, millSourcePath / 'srcPlay )
  def artifactName = "play"
}

object DocProj extends Common
{
    def moduleDeps = Seq(Macros)
    def ivyDeps = Agg(ivy"org.scalafx::scalafx:8.0.144-R12")
	def sources = T.sources( Core.millSourcePath / 'src, FxStrat.millSourcePath / 'src, FxStrat.millSourcePath / 'srcPlay)	
}
