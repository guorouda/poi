package com.ron.view;

import com.ron.Command;

public class ContainerAction extends Command {

	@Override
	public String list2() {
		String result = "[ { \"name\": \"Kitchen Sink\", \"thumb\": \"sink.png\", \"url\": \"kitchensink\", \"type\": \"Application\" }, { \"name\": \"Twitter app\", \"thumb\": \"twitter.png\", \"url\": \"twitter\", \"type\": \"Application\" }, { \"name\": \"Kiva app\", \"thumb\": \"kiva.png\", \"url\": \"kiva\", \"type\": \"Application\" }, { \"name\": \"Geocongress\", \"thumb\": \"geocongress.png\", \"url\": \"geocongress\", \"type\": \"Application\" }, { \"name\": \"AJAX\", \"thumb\": \"ajax.png\", \"url\": \"ajax\", \"type\": \"Example\" }, { \"name\": \"Carousel\", \"thumb\": \"carousel.png\", \"url\": \"carousel\", \"type\": \"Example\" }, { \"name\": \"Drag &amp; Drop\", \"thumb\": \"DnD.png\", \"url\": \"dragdrop\", \"type\": \"Example\" }, { \"name\": \"Forms\", \"thumb\": \"forms.png\", \"url\": \"forms\", \"type\": \"Example\" }, { \"name\": \"Guide\", \"thumb\": \"guide.png\", \"url\": \"guide\", \"type\": \"Example\" }, { \"name\": \"Icons\", \"thumb\": \"icons.png\", \"url\": \"icons\", \"type\": \"Example\" }, { \"name\": \"Map\", \"thumb\": \"map.png\", \"url\": \"map\", \"type\": \"Example\" }, { \"name\": \"Nested List\", \"thumb\": \"nestedList.png\", \"url\": \"nestedlist\", \"type\": \"Example\" }, { \"name\": \"Overlays\", \"thumb\": \"overlays.png\", \"url\": \"overlays\", \"type\": \"Example\" }, { \"name\": \"Picker\", \"thumb\": \"picker.png\", \"url\": \"picker\", \"type\": \"Example\" }, { \"name\": \"Sortable\", \"thumb\": \"sortable.png\", \"url\": \"sortable\", \"type\": \"Example\" }, { \"name\": \"Tabs\", \"thumb\": \"tabs.png\", \"url\": \"tabs\", \"type\": \"Example\" }, { \"name\": \"Tabs 2\", \"thumb\": \"tabs2.png\", \"url\": \"tabs2\", \"type\": \"Example\" }, { \"name\": \"Toolbars\", \"thumb\": \"toolbars.png\", \"url\": \"toolbars\", \"type\": \"Example\" }, { \"name\": \"YQL\", \"thumb\": \"yql.png\", \"url\": \"yql\", \"type\": \"Example\" }]";
		return result;
	}

	@Override
	public String list(){
		return "{\"count\":\"1\", \"user\":[{\"filename\":\"sara_pink.jpg\",\"duration\":\"2154\",\"id\":\"1407243286000\",\"uuid\":\"2222222222222222\"}]}";
	}
	
	public String update(){
		
		return "";
	}
	
}
