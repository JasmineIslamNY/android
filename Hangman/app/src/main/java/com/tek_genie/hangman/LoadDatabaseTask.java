package com.tek_genie.hangman;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


/**
 * Created by jasmineislam on 6/27/16.
 */
public class LoadDatabaseTask extends AsyncTask <String, Void, Void> {
    private Context context;
    private String [][] names = new String[][]{
            {"Khaleda", "Zia", "0", "https://en.wikipedia.org/wiki/Khaleda_Zia", "https://upload.wikimedia.org/wikipedia/commons/8/8b/Khaleda_Zia_former_Prime_Minister_of_Bangladesh_cropped.jpg", "Begum Khaleda Zia (born 15 August 1945) is a Bangladeshi politician who was the Prime Minister of Bangladesh from 1991 to 1996 and again from 2001 to 2006. When she took office in 1991, she was the first woman in the country's history and second in the Muslim world (after Benazir Bhutto of Pakistan in 1988–1990) to head a democratic government as prime minister. Zia was the First Lady of Bangladesh during the presidency of her husband Ziaur Rahman. She is the chairperson and leader of the Bangladesh Nationalist Party (BNP) which was founded by Rahman in the late 1970s.\nAfter a military coup in 1982, led by Army Chief General Hussain Muhammad Ershad, Zia helped lead the continuing movement for democracy until the fall of military dictator Ershad in 1990. Khaleda became prime minister following the victory of the BNP in the 1991 general election. She also served briefly in the short-lived government in 1996, when other parties had boycotted the first election. In the next round of general elections of 1996, the Awami League came to power. Her party came to power again in 2001. She has been elected to five separate parliamentary constituencies in the general elections of 1991, 1996 and 2001.\nIn its list of the 100 Most Powerful Women in the World, Forbes magazine ranked Zia at number 14 in 2004, number 29 in 2005, and number 33 in 2006.\nFollowing her government's term end in 2006, the scheduled January 2007 elections were delayed due to political violence and in-fighting, resulting in a bloodless military takeover of the caretaker government. During its interim rule, it charged Zia and her two sons with corruption.\nFor the better part of the last two decades, Khaleda's chief rival has been Awami League leader Sheikh Hasina. The two women have alternated as non-interim prime ministers since 1991.","Second woman in the Muslim world to head a democratic government as prime minister."},
            {"Barack", "Obama", "1", "https://en.wikipedia.org/wiki/Barack_Obama", "https://upload.wikimedia.org/wikipedia/commons/8/8d/President_Barack_Obama.jpg", "Barack Hussein Obama II (born August 4, 1961) is an American politician serving as the 44th President of the United States. He is the first African American to hold the office, as well as the first president born outside of the continental United States. Born in Honolulu, Hawaii, Obama is a graduate of Columbia University and Harvard Law School, where he served as president of the Harvard Law Review. He was a community organizer in Chicago before earning his law degree. He worked as a civil rights attorney and taught constitutional law at the University of Chicago Law School between 1992 and 2004. He served three terms representing the 13th District in the Illinois Senate from 1997 to 2004.", "2009 Nobel Peace Prize laureate."},
            {"Tony", "Blair", "1", "https://en.wikipedia.org/wiki/Tony_Blair", "https://upload.wikimedia.org/wikipedia/commons/a/ae/MSC_2014_Blair_Mueller_MSC2014_%28cropped%29.jpg","Anthony Charles Lynton Blair (born 6 May 1953), originally known as Anthony Blair, but later as Tony Blair, is a British Labour Party politician, who served as the Prime Minister of the United Kingdom (UK), from 1997 to 2007. From 1983 to 2007, Blair was the Member of Parliament (MP) for Sedgefield, and from 1994 to 2007, Blair was the Leader of the Labour Party. He now runs a consultancy business and performs charitable work.", "Strongly supported much of the foreign policy of U.S. President George W. Bush, and ensured that British Armed Forces participated in the 2001 invasion of Afghanistan and, more controversially, the 2003 invasion of Iraq."},
            {"Joe", "Biden", "1", "https://en.wikipedia.org/wiki/Joe_Biden", "https://upload.wikimedia.org/wikipedia/commons/e/ea/Official_portrait_of_Vice_President_Joe_Biden.jpg", "Joseph Robinette \"Joe\" Biden Jr. (born November 20, 1942) is an American politician who is the 47th and current Vice President of the United States, jointly elected twice with President Barack Obama, and in office since 2009. A member of the Democratic Party, Biden represented Delaware as a United States Senator from 1973 until becoming Vice President in 2009.", "First Delawarean to be Vice President of the United States."},
            {"Donald","Trump", "1", "https://en.wikipedia.org/wiki/Donald_Trump", "https://upload.wikimedia.org/wikipedia/commons/d/d2/Donald_Trump_August_19%2C_2015_%28cropped%29.jpg", "Donald John Trump (born June 14, 1946) is an American businessman, politician, television personality, author, and the presumptive nominee of the Republican Party for President of the United States in the 2016 election. Trump is the Chairman and President of The Trump Organization, as well as the founder of the gaming and hotel enterprise Trump Entertainment Resorts (now owned by Carl Icahn).", "First campaigned for the U.S. presidency in 2000, and withdrew before any votes were cast, but afterwards won two Reform Party primaries."},
            {"David", "Cameron", "1", "https://en.wikipedia.org/wiki/David_Cameron", "https://upload.wikimedia.org/wikipedia/commons/2/21/David_Cameron_official.jpg", "David William Donald Cameron (born 9 October 1966) is a British politician. Cameron has served as Prime Minister of the United Kingdom since 2010, and as Member of Parliament for Witney since 2001. The Leader of the Conservative Party since 2005, Cameron identifies as a One-Nation Conservative, and has been associated with both economically liberal and socially liberal policies. After the referendum on leaving the European Union, Cameron announced that he would leave office by October 2016 (after a new Party leader is elected).", "Announced he would leave after Britain decided to leave."},
            {"George W", "Bush", "1", "https://en.wikipedia.org/wiki/George_W._Bush", "https://upload.wikimedia.org/wikipedia/commons/d/d4/George-W-Bush.jpeg", "George Walker Bush (born July 6, 1946) is an American politician who served as the 43rd President of the United States from 2001 to 2009 and 46th Governor of Texas from 1995 to 2000. The eldest son of Barbara and George H. W. Bush, he was born in New Haven, Connecticut. He co-owned the Texas Rangers baseball team before defeating Ann Richards in the 1994 Texas gubernatorial election. He was elected president in 2000 after a close and controversial election against Al Gore, becoming the fourth president to be elected while receiving fewer popular votes nationwide than an opponent.", "The second president to have been the son of a former president."},
            {"Bernie", "Sanders", "1", "https://en.wikipedia.org/wiki/Bernie_Sanders", "https://upload.wikimedia.org/wikipedia/commons/d/de/Bernie_Sanders.jpg", "Bernard \"Bernie\" Sanders (born September 8, 1941) is an American politician and the junior United States senator from Vermont. He is a candidate for the Democratic nomination for President of the United States in the 2016 election. A member of the Democratic Party since 2015, Sanders had been the longest-serving independent in U.S. congressional history, though his caucusing with the Democrats entitled him to committee assignments and at times gave Democrats a majority. Sanders became the ranking minority member on the Senate Budget Committee in January 2015; he had previously served for two years as chair of the Senate Veterans' Affairs Committee. A self-proclaimed democratic socialist, Sanders is pro-labor and favors greater economic equality.", "Rose to national prominence following his 2010 filibuster against the proposed extension of the Bush tax cuts."},
            {"Dick", "Cheney", "1", "https://en.wikipedia.org/wiki/Dick_Cheney", "https://upload.wikimedia.org/wikipedia/commons/8/88/46_Dick_Cheney_3x4.jpg", "Richard Bruce Cheney, generally known as Dick Cheney (born January 30, 1941) is an American politician and businessman who was the 46th Vice President of the United States from 2001 to 2009, under President George W. Bush. He served as the White House Chief of Staff, from 1975 to 1977 and the Secretary of Defense during the Presidency of George H. W. Bush.","Chairman and CEO of Halliburton Company from 1995 to 2000."},
            {"Al","Gore","1","https://en.wikipedia.org/wiki/Al_Gore", "https://upload.wikimedia.org/wikipedia/commons/c/c5/Al_Gore%2C_Vice_President_of_the_United_States%2C_official_portrait_1994.jpg", "Albert Arnold \"Al\" Gore Jr. (born March 31, 1948) is an American politician and environmentalist who served as the 45th Vice President of the United States from 1993 to 2001 under President Bill Clinton. Chosen as Clinton's running mate in their successful 1992 campaign, he was reelected in 1996. At the end of Clinton's second term, Gore was the Democratic Party's nominee for President in 2000. After leaving office, Gore remained prominent as an author and environmental activist, whose work in climate change activism earned him (jointly with the IPCC) the Nobel Peace Prize in 2007.", "Won the popular vote but lost in the Electoral College."},
            {"Bill", "Clinton", "1", "https://en.wikipedia.org/wiki/Bill_Clinton", "https://upload.wikimedia.org/wikipedia/commons/4/49/44_Bill_Clinton_3x4.jpg", "William Jefferson Clinton (born William Jefferson Blythe III, August 19, 1946) is an American politician who was the 42nd President of the United States from 1993 to 2001. Clinton was previously Governor of Arkansas from 1979 to 1981 and 1983 to 1992, and the Arkansas Attorney General from 1977 to 1979. A member of the Democratic Party, ideologically Clinton was a New Democrat, and many of his policies reflected a centrist \"Third Way\" philosophy of governance.","First Baby Boomer President."},
            {"Angela", "Merkel", "1", "https://en.wikipedia.org/wiki/Angela_Merkel", "https://upload.wikimedia.org/wikipedia/commons/9/94/Angela_Merkel_2013_%28cropped%29.jpg", "Angela Dorothea Merkel (born 17 July 1954) is a German politician and former research scientist. Merkel has been the Chancellor of Germany since 2005, and the leader of the Christian Democratic Union (CDU) since 2000.", "Named the most powerful woman in the world for a record ninth time by Forbes in May 2015."},
            {"Hillary", "Clinton", "1", "https://en.wikipedia.org/wiki/Hillary_Clinton", "https://upload.wikimedia.org/wikipedia/commons/2/27/Hillary_Clinton_official_Secretary_of_State_portrait_crop.jpg", "Hillary Diane Rodham Clinton (born October 26, 1947) is an American politician and a candidate for the Democratic presidential nomination in the 2016 election. She served as the 67th United States Secretary of State from 2009 to 2013, the junior United States Senator representing New York from 2001 to 2009, First Lady of the United States during the presidency of Bill Clinton from 1993 to 2001, and First Lady of Arkansas for twelve years.", "The first female senator from New York"},
            {"Shinzo", "Abe", "1", "https://en.wikipedia.org/wiki/Shinz%C5%8D_Abe", "https://upload.wikimedia.org/wikipedia/commons/e/e9/Shinz%C5%8D_Abe_April_2015.jpg", "Shinzō Abe (born 21 September 1954) is the current Prime Minister of Japan, re-elected to the position in December 2012. Abe is also the President of the Liberal Democratic Party (LDP). Abe served for a year as Prime Minister, from 2006 to 2007. Hailing from a politically prominent family, at age 52, Abe became Japan's youngest post-war prime minister, and the first to be born after World War II, when he was elected by a special session of the National Diet in September 2006. Abe resigned on 12 September 2007, for health reasons. Abe was replaced by Yasuo Fukuda, beginning a string of five Prime Ministers, none of whom retained office for more than sixteen months, before Abe staged a political comeback.","Japanese PM whose name is part of a portmanteau with economics for his policies."},
            {"Martin","O'Malley","1","https://en.wikipedia.org/wiki/Martin_O%27Malley","https://upload.wikimedia.org/wikipedia/commons/9/97/Martin_O%27Malley%2C_photo_portrait%2C_visiting_Maryland_National_Guard%2C_June_8%2C_2008.jpg","Martin Joseph O'Malley (born January 18, 1963) is an American politician who was the 61st Governor of Maryland from 2007 to 2015. Prior to being elected as governor, he served as the Mayor of Baltimore from 1999 to 2007 and was a Baltimore City Councilor from 1991 to 1999. \nO'Malley served as the chair of the Democratic Governors Association from 2011 to 2013, while being governor of Maryland.\nAs governor, in 2011, he signed a law that would make illegal immigrants brought to the United States as children eligible for in-state college tuition, and in 2012, he signed a law to legalize same-sex marriage in Maryland. Each law was put to a voter referendum in the 2012 general election and upheld by a majority of the voting public.\nO'Malley publicly announced his candidacy in the 2016 presidential election on May 30, 2015, in Baltimore, Maryland. On February 1, 2016, he suspended his campaign after finishing third in the Iowa caucus.","Finished third in the Democratic Iowa caucus."},
            {"John","Kasich","1","https://en.wikipedia.org/wiki/John_Kasich","https://upload.wikimedia.org/wikipedia/commons/a/ab/Governor_John_Kasich.jpg","John Richard Kasich (born May 13, 1952) is an American politician, the 69th and current Governor of Ohio, first elected in 2010 and re-elected in 2014.\nKasich served nine terms as a member of the United States House of Representatives, representing Ohio's 12th congressional district from 1983 to 2001. His tenure in the House included 18 years on the House Armed Services Committee and six years as chairman of the House Budget Committee. He was a key figure in the passage of both welfare reform and the Balanced Budget Act of 1997.\nHe was a commentator on Fox News Channel, hosting Heartland with John Kasich from 2001 to 2007. He also worked as an investment banker, serving as managing director of the Lehman Brothers office in Columbus, Ohio.\nKasich unsuccessfully sought the Republican nomination for president in 2000 and in 2016. During the 2016 campaign, he was able to gain 161 delegates, and was the last candidate to drop out.","Won 161 delegates in 2016 Republican primary."},
            {"Ted","Cruz","1","https://en.wikipedia.org/wiki/Ted_Cruz","https://upload.wikimedia.org/wikipedia/commons/8/87/Ted_Cruz%2C_official_portrait%2C_113th_Congress.jpg","Rafael Edward \"Ted\" Cruz (born December 22, 1970) is an American attorney and politician; he was elected in 2012 as the junior United States Senator from Texas.\nCruz graduated from Princeton University in 1992 and from Harvard Law School in 1995. He was the first Hispanic, and the longest-serving, Solicitor General in Texas history.\nCruz ran for the Senate seat vacated by fellow Republican Kay Bailey Hutchison and, in July 2012, defeated Lieutenant Governor David Dewhurst during the Republican primary runoff, 57%–43%. He is the first Hispanic American to serve as a U.S. senator representing Texas, and is one of three senators of Cuban descent. He chairs the Senate Judiciary Subcommittee on Oversight, Federal Rights and Agency Activities and is also the chairman of the Senate Commerce Subcommittee on Space, Science and Competitiveness. In November 2012, he was appointed vice-chairman of the National Republican Senatorial Committee.\nCruz began campaigning for the Republican presidential nomination in March 2015. During the primary campaign, his base of support was strongest with \"women, white evangelical Protestants, people over the age of 50, and those who identified themselves as conservatives\", though he had crossover appeal to other factions within his party, including libertarian conservatives and millennials. His victory in the February 2016 Iowa caucuses marked the first time a Hispanic person won a presidential caucus or primary.\nHe suspended his campaign for President on May 3, 2016, after losing the Republican primary in Indiana to Donald Trump.","First Hispanic person to win a presidential caucus or primary."},
            {"Marco","Rubio","1","https://en.wikipedia.org/wiki/Marco_Rubio","https://upload.wikimedia.org/wikipedia/commons/7/79/Marco_Rubio%2C_Official_Portrait%2C_112th_Congress.jpg","Marco Antonio Rubio (born May 28, 1971) is an American politician and attorney, and the junior United States Senator from Florida. Rubio previously served as Speaker of the Florida House of Representatives.\nRubio successfully ran for United States Senate in 2010. In the U.S. Senate, he chairs the Commerce Subcommittee on Oceans, Atmosphere, Fisheries, and Coast Guard, as well as the Foreign Relations Subcommittee on Western Hemisphere, Transnational Crime, Civilian Security, Democracy, Human Rights and Global Women\'s Issues. He is one of three Latino Americans serving in the Senate.\nIn April 2015, Rubio announced that he would forgo seeking reelection to the Senate to run for President. He suspended his campaign for President on March 15, 2016, after losing the Republican primary in his home state of Florida to Donald Trump. On June 22, 2016, he reversed his decision and announced his reelection campaign for the Senate.","Changed his mind about running for the junior Senate seat from Florida"},
            {"Ben","Carson","1","https://en.wikipedia.org/wiki/Ben_Carson","https://upload.wikimedia.org/wikipedia/commons/f/f1/Ben_Carson_by_Skidmore_with_lighting_correction.jpg","Benjamin Solomon \"Ben\" Carson, Sr. (born September 18, 1951) is a retired American neurosurgeon and former candidate for President of the United States. Born in Detroit, Michigan, and a graduate of Yale University and the University of Michigan Medical School, Carson has authored numerous books on his medical career and political stances, and was the subject of a television drama film in 2009.\nHe was the Director of Pediatric Neurosurgery at Johns Hopkins Hospital in Maryland from 1984 until his retirement in 2013. Among his achievements as a surgeon were separating conjoined twins and developing a hemispherectomy technique for controlling seizures. Both achievements were recognized in 2008 with the Presidential Medal of Freedom.\nCarson's widely publicized speech at the 2013 National Prayer Breakfast catapulted him to conservative fame for his views on social and political issues. On May 4, 2015, Carson announced he was running for the Republican nomination in the 2016 presidential election at a rally in his hometown of Detroit.\nHe suspended his campaign on March 4 and announced he would be the new national chairman of My Faith Votes, a group that encourages Christians to exercise their civic duty to vote. On March 11, 2016, Carson endorsed the candidacy of Donald Trump and at the press conference stated that Trump had a \"cerebral\" side.","Presidential candidate known for separating conjoined twins."},
            {"Jeb","Bush","1","https://en.wikipedia.org/wiki/Jeb_Bush","https://upload.wikimedia.org/wikipedia/commons/c/ca/Jeb_Bush_at_Southern_Republican_Leadership_Conference_May_2015_by_Vadon_02.jpg","John Ellis \"Jeb\" Bush (born February 11, 1953) is an American businessman and politician who served as the 43rd Governor of Florida from 1999 to 2007.\nBush, who grew up in Houston, is the second son of former President George H. W. Bush and former First Lady Barbara Bush, and a younger brother of former President George W. Bush.\nIn 1994, Bush made his first run for office, losing the election for governor by less than two percentage points to the incumbent, Lawton Chiles. Bush ran again in 1998 and defeated Lieutenant Governor Buddy MacKay with 55 percent of the vote. He ran for reelection in 2002 defeating Bill McBride and won with 56 percent to become Florida's first two-term Republican governor.\nDuring his eight years as governor, Bush pushed an ambitious Everglades conservation plan, supported caps for medical malpractice litigation, launched a Medicaid privatization pilot program, and instituted reforms to the state education system, including the issuance of vouchers and promoting school choice.\nBush announced his presidential candidacy on June 15, 2015, but suspended his campaign on February 20, 2016, shortly after the South Carolina primary, and endorsed Senator Ted Cruz on March 23, 2016.","If he had won the presidency, would have been the first to have a father and brother as former presidents."},
            {"Chris","Christie","1","https://en.wikipedia.org/wiki/Chris_Christie","https://upload.wikimedia.org/wikipedia/commons/5/56/Chrisgov.jpg","Christopher James \"Chris\" Christie (born September 6, 1962) is an American attorney, politician and member of the Republican Party who has been the 55th Governor of New Jersey since January 2010. His term ends January 23, 2018.\nIn January 2009, Christie declared his candidacy for Governor of New Jersey. He won the Republican primary, and defeated incumbent Governor Jon Corzine in the election that November. On November 21, 2013, Christie was elected Chairman of the Republican Governors Association.\nChristie was seen as a potential candidate in the 2012 presidential election, and though not running, he was the keynote speaker at the 2012 Republican National Convention.\nOn June 30, 2015, Christie announced his candidacy for the Republican nomination in the 2016 presidential election. He suspended his candidacy on February 10, 2016, and later endorsed Donald Trump for president.","Former US Attorney who ran for US president in 2016."},
            {"Carly","Fiorina","1","https://en.wikipedia.org/wiki/Carly_Fiorina","https://upload.wikimedia.org/wikipedia/commons/1/1e/Carly_Fiorina_by_Gage_Skidmore_3.jpg","Cara Carleton \"Carly\" Fiorina (née Sneed; born September 6, 1954) is an American businessperson and political candidate. Fiorina is known primarily for her tenure as the Chief Executive Officer (CEO) of Hewlett-Packard (HP).\nAs the CEO of HP from 1999 to 2005, Fiorina was the first woman to lead a Top-20 company as ranked by Fortune Magazine. In 2002, Fiorina oversaw what was then the largest technology sector merger in history, in which HP acquired their rival personal computer manufacturer, Compaq. As the CEO, Fiorina subsequently laid off 30,000 U.S. employees, a decision which supporters say saved the company, preserving the jobs of thousands of employees. In February 2005, she was fired as the Chairman and CEO after a boardroom disagreement.\nIn 2010, she won the Republican nomination for the United States Senate in California; however, she lost the general election to incumbent Democrat Barbara Boxer. Fiorina was a major candidate in the 2016 Republican presidential primary, and was briefly the vice-presidential running mate of Ted Cruz, until he suspended his campaign for President on May 3, 2016.","The first woman to lead a Top-20 company as ranked by Fortune Magazine."},
            {"Rand","Paul","1","https://en.wikipedia.org/wiki/Rand_Paul","https://upload.wikimedia.org/wikipedia/commons/7/78/Rand_Paul%2C_official_portrait%2C_112th_Congress_alternate.jpg","Randal Howard \"Rand\" Paul (born January 7, 1963) is an American politician and physician. Since 2011, Paul has served in the United States Senate as a member of the Republican Party representing Kentucky. He is the son of former U.S. Representative Ron Paul of Texas.\nThroughout Paul's life, he volunteered for his father's campaigns. In 2010, Paul entered politics by running for a seat in the United States Senate. Paul has described himself as a Constitutional conservative and a supporter of the Tea Party movement and has advocated for a balanced budget amendment, term limits, and privacy reform.\nOn April 7, 2015, Paul officially announced his candidacy for the Republican nomination at the 2016 U.S. presidential election. He suspended his campaign on February 3, 2016, shortly after the Iowa caucus.","Doctor who ran for president and also the son of another doctor who ran for president."},
            {"Mike","Huckabee","1","https://en.wikipedia.org/wiki/Mike_Huckabee","https://upload.wikimedia.org/wikipedia/commons/4/4b/Mike_Huckabee_by_Gage_Skidmore_6.jpg","Michael Dale \"Mike\" Huckabee (born August 24, 1955) is an American politician, Christian minister, author, musician, and commentator who served as the 44th Governor of Arkansas from 1996 to 2007. He was a candidate in the United States Republican presidential primaries in both 2008 and 2016. He won the 2008 Iowa Republican caucuses and finished second in delegate count and third in both popular vote and number of states won, behind nominee John McCain and Mitt Romney.\nBeginning in 2008, Huckabee hosted the Fox News Channel talk show Huckabee, ending the show in January 2015 in order to explore a potential bid for the presidency.\nHuckabee announced his candidacy for the Republican nomination in the 2016 presidential election, in Hope, Arkansas, on May 5, 2015. It was his second run for the U.S. presidency. He suspended his campaign on February 1, 2016.","Candidate in the United States Republican presidential primaries in both 2008 and 2016."},
            {"Sheikh", "Hasina","0", "https://en.wikipedia.org/wiki/Sheikh_Hasina", "https://upload.wikimedia.org/wikipedia/commons/e/e1/Sheikh_Hasina_in_London_2011.jpg", "Sheikh Hasina Wazed (born 28 September 1947) is the current Prime Minister of Bangladesh, in office since January 2009. She previously served as Prime Minister from 1996 to 2001, and she has led the Bangladesh Awami League since 1981.\nHasina\'s political career has spanned more than four decades during which she has been both Prime Minister and opposition leader. As opposition leader, she was the target of an assassination attempt in 2004. In 2007, she was arrested for corruption and charged with murder by the military-backed Caretaker Government during the 2006–2008 Bangladeshi political crisis, when the generals imposed a state of emergency. She returned as Prime Minister after a landslide victory for the Awami League-led Grand Alliance in 2008, when they took two-thirds of the seats in parliament. In January 2014 she became the prime minister for the third time after winning the 2014 parliamentary election, which was boycotted by the main opposition BNP-led alliance. Hasina is considered one of the most powerful women in the world, ranking 47th on Forbes' list of the 100 most powerful women in the world in 2014. For the better part of the last two decades, Hasina\'s chief rival has been BNP leader Khaleda Zia. The two women have alternated as non-interim Prime Ministers since 1991.","Prime minister of the 8th largest country in the world by population."},
            {"Adolf", "Hitler", "0", "https://en.wikipedia.org/wiki/Adolf_Hitler", "https://upload.wikimedia.org/wikipedia/commons/a/ab/Bundesarchiv_Bild_183-H1216-0500-002%2C_Adolf_Hitler.jpg", "Adolf Hitler (20 April 1889 – 30 April 1945) was a German politician who was the leader of the Nazi Party (Nationalsozialistische Deutsche Arbeiterpartei; NSDAP), Chancellor of Germany from 1933 to 1945, and Führer (\"leader\") of Nazi Germany from 1934 to 1945. As dictator of Nazi Germany, he initiated World War II in Europe with the invasion of Poland in September 1939 and was a central figure of the Holocaust.\nHitler was born into a German-speaking Austrian family and raised near Linz. He moved to Germany in 1913 and was decorated during his service in the German Army in World War I. He joined the German Workers\' Party, the precursor of the NSDAP, in 1919 and became leader of the NSDAP in 1921. In 1923, he attempted a coup in Munich to seize power. The failed coup resulted in Hitler\'s imprisonment, during which time he dictated the first volume of his autobiography and political manifesto Mein Kampf (\"My Struggle\"). After his release in 1924, Hitler gained popular support by attacking the Treaty of Versailles and promoting Pan-Germanism, anti-Semitism, and anti-communism with charismatic oratory and Nazi propaganda. Hitler frequently denounced international capitalism and communism as being part of a Jewish conspiracy.\nBy 1933, the Nazi Party was the largest elected party in the German Reichstag, which led to Hitler's appointment as Chancellor on 30 January 1933. Following fresh elections won by his coalition, the Reichstag passed the Enabling Act, which began the process of transforming the Weimar Republic into Nazi Germany, a one-party dictatorship based on the totalitarian and autocratic ideology of National Socialism. Hitler aimed to eliminate Jews from Germany and establish a New Order to counter what he saw as the injustice of the post-World War I international order dominated by Britain and France. His first six years in power resulted in rapid economic recovery from the Great Depression, the effective abandonment of restrictions imposed on Germany after World War I, and the annexation of territories that were home to millions of ethnic Germans—actions which gave him significant popular support.\nHitler sought Lebensraum (\"living space\") for the German people. His aggressive foreign policy is considered to be the primary cause of the outbreak of World War II in Europe. He directed large-scale rearmament and on 1 September 1939 invaded Poland, resulting in British and French declarations of war on Germany. In June 1941, Hitler ordered an invasion of the Soviet Union. By the end of 1941 German forces and the European Axis powers occupied most of Europe and North Africa. Failure to defeat the Soviets and the entry of the United States into the war forced Germany onto the defensive and it suffered a series of escalating defeats. In the final days of the war, during the Battle of Berlin in 1945, Hitler married his long-time lover, Eva Braun. On 30 April 1945, less than two days later, the two killed themselves to avoid capture by the Red Army, and their corpses were burned.\nUnder Hitler\'s leadership and racially motivated ideology, the Nazi regime was responsible for the genocide of at least 5.5 million Jews and millions of other victims whom he and his followers deemed Untermenschen (\"sub-humans\") and socially undesirable. Hitler and the Nazi regime were also responsible for the killing of an estimated 19.3 million civilians and prisoners of war. In addition, 29 million soldiers and civilians died as a result of military action in the European Theatre of World War II. The number of civilians killed during the Second World War was unprecedented in warfare, and constitutes the deadliest conflict in human history.", "Advocated for his country to be run under the ideology of National Socialism"},
            {"Benito", "Mussolini", "0", "https://en.wikipedia.org/wiki/Benito_Mussolini", "https://upload.wikimedia.org/wikipedia/en/6/67/Mussolini_biografia.jpg", "Benito Amilcare Andrea Mussolini (29 July 1883 – 28 April 1945) was an Italian politician, journalist, and leader of the National Fascist Party, ruling the country as Prime Minister from 1922 until he was ousted in 1943. He ruled constitutionally until 1925, when he dropped all pretense of democracy and set up a legal dictatorship. Known as Il Duce (The Leader), Mussolini was the founder of Italian fascism.\nIn 1912 Mussolini was the leading member of the National Directorate of the Italian Socialist Party (PSI). Prior to 1914 he was a keen supporter of the Socialist International, starting the series of meetings in Switzerland that organized the communist revolutions and insurrections that swept through Europe from 1917. Mussolini was expelled from the PSI due to his opposition to the party's stance on neutrality in World War I. Mussolini denounced the PSI, and later founded the fascist movement. Following the March on Rome in October 1922 he became the youngest Prime Minister in Italian history until the appointment of Matteo Renzi in February 2014. After destroying all political opposition through his secret police and outlawing labor strikes, Mussolini and his fascist followers consolidated their power through a series of laws that transformed the nation into a one-party dictatorship. Within five years he had established dictatorial authority by both legal and extraordinary means, aspiring to create a totalitarian state. Mussolini remained in power until he was deposed by King Victor Emmanuel III in 1943. A few months later, he became the leader of the Italian Social Republic, a German client regime in northern Italy; he held this post until his death in 1945.\nMussolini sought to delay a major war in Europe until at least 1942. Germany invaded Poland on 1 September 1939, starting World War II. On 10 June 1940, with the Fall of France imminent, Mussolini officially entered the war on the side of Germany, though he was aware that Italy did not have the military capacity to carry out a long war with the United Kingdom. Mussolini believed that after the imminent French armistice, Italy could gain territorial concessions from France and then he could concentrate his forces on a major offensive in Egypt, where British and Commonwealth forces were outnumbered by Italian forces. However the UK refused to accept German proposals for a peace that would involve accepting Germany\'s victories in Eastern and Western Europe, plans for an invasion of the UK did not proceed, and the war continued. In the summer of 1941 Mussolini sent Italian forces to participate in the invasion of the Soviet Union, and war with the United States followed in December.\nOn 24 July 1943, soon after the start of the Allied invasion of Italy, the Grand Council of Fascism voted against him, and the King had him arrested the following day. On 12 September 1943, Mussolini was rescued from prison in the Gran Sasso raid by German special forces. In late April 1945, with total defeat looming, Mussolini attempted to escape north, only to be quickly captured and summarily executed near Lake Como by Italian Communists. His body was then taken to Milan where it was hung upside down at a service station for public viewing and to provide confirmation of his demise.", "Was known as Il Duce"},
            {"Theresa", "May", "0", "https://en.wikipedia.org/wiki/Theresa_May", "https://upload.wikimedia.org/wikipedia/en/a/ae/Theresa_May_UK_Home_Office_%28cropped%29.jpg", "Theresa Mary May (née Brasier; born 1 October 1956) is the Prime Minister of the United Kingdom and Leader of the Conservative Party. She has also been the Member of Parliament (MP) for Maidenhead since 1997. May identifies as a One-Nation Conservative and is characterized as a liberal conservative.\nThe daughter of a vicar, May grew up in Oxfordshire. From 1977 until 1983, she worked for the Bank of England, and from 1985 until 1997 at the Association for Payment Clearing Services, also serving as a councillor for Durnsford in Merton. After unsuccessful attempts to be elected to the House of Commons in 1992 and 1994, she was elected as the MP for Maidenhead in the 1997 general election. From 1999 to 2010, May served in a number of roles in the Shadow Cabinets of William Hague, Iain Duncan Smith, Michael Howard, and David Cameron, including Shadow Transport Secretary and Shadow Work and Pensions Secretary. She was also Chairman of the Conservative Party from 2002 to 2003.\nAfter the formation of the Coalition Government following the 2010 general election, May was appointed Home Secretary and Minister for Women and Equalities, giving up the latter role in 2012. Reappointed after the Conservative victory in the 2015 general election, she went on to become the longest-serving Home Secretary since James Chuter Ede over 60 years previously, pursuing reform of the police, taking a harder line on drug policy, overseeing the creation of elected Police and Crime Commissioners and introducing restrictions on immigration.\nFollowing the resignation of David Cameron on 24 June 2016, May announced her candidacy for the leadership of the Conservative Party and quickly emerged as the front-runner. She won the first ballot of Conservative MPs on 5 July by a significant margin, and two days later won the votes of 199 MPs, going forward to face a vote of Conservative Party members in a contest with Andrea Leadsom. Leadsom\'s withdrawal from the election on 11 July led to May\'s appointment as leader the same day. She was appointed Prime Minister two days later, the second woman to hold that office.", "Second female prime minister of the United Kingdom"}
    };

    public LoadDatabaseTask (Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        HangmanDAO dao = new HangmanDAO(context);
        for (int i=0; i < names.length; i++) {
            Log.i("LoadDatabase", "In loadDB: name[0] " + names[i][0]);
            Log.i("LoadDatabase", "In loadDB: name[1] " + names[i][1]);
            Log.i("LoadDatabase", "In loadDB: name[2] " + String.valueOf(names[i][2]));
            Log.i("LoadDatabase", "In loadDB: name[3] " + names[i][3]);
            Log.i("LoadDatabase", "In loadDB: name[4] " + names[i][4]);
            Log.i("LoadDatabase", "In loadDB: name[5] " + names[i][5]);
            Log.i("LoadDatabase", "In loadDB: name[6] " + names[i][6]);
            dao.saveName(names[i]);
        }
        return null;
    }

}



