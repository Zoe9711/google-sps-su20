// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data/translation")
public class DataServlet_translate extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Task").addSort("timestamp", SortDirection.DESCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
    ArrayList<String> comments = new ArrayList<>();

    Translate translate = TranslateOptions.getDefaultInstance().getService();
    for (Entity entity : results.asIterable()) {
        String original = (String) entity.getProperty("text-input");
        Translation translation = translate.translate(original, Translate.TranslateOption.targetLanguage("ja"));
        comments.add(" " + translation.getTranslatedText());
    }
 
    String json = convertToJsonUsingGson(comments);
    response.setContentType("application/json;");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().println(json);
  }

  private String convertToJsonUsingGson(ArrayList<String> myStrArr) {
    Gson gson = new Gson();
    String json = gson.toJson(myStrArr);
    return json;
  }
}

