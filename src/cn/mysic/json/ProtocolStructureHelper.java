package cn.mysic.json;

import adl.StringHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.security.SecureRandom;
import java.util.*;

/**
 * Created by lxu on 3/18/14.
 */
public class ProtocolStructureHelper {
    private static final Gson gson = new Gson();

    public static List<List<String>> readFile(String fileName) throws Exception {
        List<List<String>> result = new ArrayList<>();
        try {
            InputStream stream = ClassLoader.getSystemResourceAsStream(fileName);

            if (stream == null) {
                stream = new FileInputStream(fileName);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            try {
                String line = br.readLine();
                while (line != null && !line.isEmpty()) {

                    List<String> oneList = new ArrayList<>();
                    oneList.addAll(Arrays.asList(line.split(",  ")));
                    result.add(oneList);
                    line = br.readLine();
                }

            } finally {
                br.close();
                stream.close();
            }

        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    public static FormatNode createRawNode(List<List<String>> input) {
        Map<String, FormatNode> allNodes = new HashMap<>();
        Map<String, String> idMap = new HashMap<>();
        String root = "";
        for (List<String> oneNode : input) {
            String id = oneNode.get(0);
            //           System.out.println(gson.toJson(oneNode));
            String pid = oneNode.get(1);
            FormatNode tmpNode = gson.fromJson(oneNode.get(2), new TypeToken<FormatNode>() {
            }.getType());
            idMap.put(id, pid);
            allNodes.put(id, tmpNode);
        }
        for (String id : idMap.keySet()) {
            String target = idMap.get(id);
            if (!target.equals("ROOT")) {
//                allNodes.get(id).setParentName(allNodes.get(target).getDisplayName());
                if (allNodes.get(id).getDisplayName().equals("NONSTANDARD")) {
                    allNodes.get(target).addChild(allNodes.get(id));
                } else {
                    allNodes.get(target).addChild(allNodes.get(id), true);
                }
            } else {
//                allNodes.get(id).setParentName("ROOT");
                root = id;
            }
        }
        return allNodes.get(root);
    }

    public static void addRemoveParent(FormatNode oneRoot, boolean remove) {
        List<FormatNode> todo = new ArrayList<>();
        todo.addAll(oneRoot.getChildren());
        for (FormatNode oneNode : oneRoot.getChildren()) {
            if (remove) {
                oneNode.setParent(null);
            } else {
                oneNode.setParent(oneRoot);
            }
        }
        while (!todo.isEmpty()) {
            FormatNode oneNode = todo.get(0);
            todo.addAll(oneNode.getChildren());
            for (FormatNode anotherNode : oneNode.getChildren()) {
                if (remove) {
                    anotherNode.setParent(null);
                } else {
                    anotherNode.setParent(oneNode);
                }
            }
            todo.remove(oneNode);
        }
    }

    private static void enActive(FormatNode oneRoot) {
        List<FormatNode> todo = new ArrayList<>();
        todo.add(oneRoot);

        while (!todo.isEmpty()) {
            FormatNode oneNode = todo.get(0);
            oneNode.setActive(true);
            todo.addAll(oneNode.getChildren());
            todo.remove(oneNode);
        }
    }

    private static void deActive(FormatNode oneRoot) {
        List<FormatNode> todo = new ArrayList<>();
        todo.add(oneRoot);

        while (!todo.isEmpty()) {
            FormatNode oneNode = todo.get(0);
            oneNode.setActive(false);
            todo.addAll(oneNode.getChildren());
            todo.remove(oneNode);
        }
    }


    private static List<FormatNode> findLeave(FormatNode oneRoot, boolean keyOnly) {
        List<FormatNode> todo = new ArrayList<>();
        todo.add(oneRoot);
        List<FormatNode> result = new ArrayList<>();
        while (!todo.isEmpty()) {
            FormatNode oneNode = todo.get(0);
            if (oneNode.getActiveChildren().isEmpty()) {
                if (keyOnly) {
                    if (oneNode.type.equals(NodeType.KEY)) {
                        result.add(0, oneNode);
                    } else {
                        result.add(0, oneNode.getParent());
                    }
                } else {
                    result.add(0, oneNode);
                }
            } else {
                todo.addAll(oneNode.getActiveChildren());
            }
            todo.remove(oneNode);
        }
        return result;
    }


    public static Map<String, List<String>> generateProtocolKeyStructure(FormatNode root) {
        Map<String, List<String>> result = new HashMap<>();
        addRemoveParent(root, false);
        enActive(root);
        List<FormatNode> todo = findLeave(root, true);
        List<String> tmpList = new ArrayList<>();
        boolean done = false;
        while (!done) {
            List<FormatNode> newTodo = new ArrayList<>();
            if (todo.isEmpty()) break;

            FormatNode oneNode = todo.get(0);
            if (oneNode.getParent() != null && oneNode.getParent().getParent() != null) {
                if (oneNode.getParent().getParent().getDisplayName().equals("协议")) {
                    tmpList.add(0, oneNode.getName());
                    if (result.keySet().contains(oneNode.getParent().getName())) {
                        result.get(oneNode.getParent().getName()).addAll(0, tmpList);

                    } else {
                        result.put(oneNode.getParent().getName(), tmpList);
                    }
                    tmpList = new ArrayList<>();

                } else {
                    newTodo.add(oneNode.getParent().getParent());
                    tmpList.add(0, oneNode.getName());
                }
            }
            todo.remove(0);
            todo.addAll(0, newTodo);
        }
        addRemoveParent(root, true);

        Map<String, List<String>> keyStructure = new HashMap<>();
        for (FormatNode oneNode : root.getChildren()) {
            String oneProtocol = oneNode.getName();
            if (result.keySet().contains(oneProtocol)) {
                Set<String> tmpSet = new LinkedHashSet<>(result.get(oneProtocol));
                keyStructure.put(oneProtocol, new ArrayList<>(tmpSet));
            } else {
                keyStructure.put(oneProtocol, new ArrayList<>());
            }

        }

        return keyStructure;

    }

    public static void modifyProtocolStructure(FormatNode root, String targetKey, int targetDiff) {
        addRemoveParent(root, false);
        enActive(root);
        List<FormatNode> todo = findLeave(root, false);
        boolean done = false;
        while (!done) {
            List<FormatNode> newTodo = new ArrayList<>();
            if (todo.isEmpty()) break;

            FormatNode oneNode = todo.get(0);
            if (oneNode.getParent() != null && oneNode.getParent().getParent() != null) {
                if (oneNode.getParent().getName().equals(targetKey)) {
                    if (!oneNode.getName().equals("NONSTANDARD")) {

                        int tmp = Integer.valueOf(oneNode.getName()) + targetDiff;
                        oneNode.setName(Integer.toString(tmp));
                        String[] otherTmp = oneNode.getDisplayName().split("\\(");
                        oneNode.setDisplayName(otherTmp[0] + "(" + tmp + ")");
                        System.out.println(oneNode.getName() + oneNode.getDisplayName());
                    }
                }
                if (oneNode.getParent().getParent().getDisplayName().equals("协议")) {

                } else {
                    newTodo.add(oneNode.getParent().getParent());
                }
            }
            todo.remove(0);
            todo.addAll(0, newTodo);
        }
        addRemoveParent(root, true);
    }


    public static void Data2Tree(String inFile, String outFile) throws Exception {
        List<List<String>> result = readFile(inFile);
        PrintWriter writer = new PrintWriter(outFile, "UTF-8");
        FormatNode root = createRawNode(result);
        writer.println(gson.toJson(root));
        writer.close();
    }

    public static FormatNode Data2Tree(String inFile) throws Exception {
        List<List<String>> result = readFile(inFile);
        return createRawNode(result);
    }

    public static void Tree2Data(String inFile, String outFile) throws Exception {
        Random rand = new SecureRandom();
        int maxInt = 99999999;

        List<String> inputList = StringHelper.readFile(inFile);
        FormatNode rootOrig = gson.fromJson(inputList.get(0), new TypeToken<FormatNode>() {
        }.getType());
        for (FormatNode oneProtocol : rootOrig.getChildren()) {
            String realFile = outFile + "_" + oneProtocol.name + ".protocolImpl";
            FormatNode root = gson.fromJson(inputList.get(0), new TypeToken<FormatNode>() {
            }.getType());
            FormatNode myProtocol = null;
            for (FormatNode canProtocol : root.getChildren()) {
                if (canProtocol.name.equals(oneProtocol.name)) {
                    myProtocol = canProtocol;
                }
            }
            root.getChildren().clear();
            root.getChildren().add(myProtocol);
            PrintWriter writer = new PrintWriter(realFile, "UTF-8");

            addRemoveParent(root, false);
            List<FormatNode> todoNode = new ArrayList<>();
            todoNode.add(root);

            Set<Integer> ParentNames = new HashSet<>();
            int tmpName;

            while (!todoNode.isEmpty()) {
                FormatNode oneNode = todoNode.get(0);
                tmpName = rand.nextInt(maxInt);
                while (ParentNames.contains(tmpName)) {
                    tmpName = rand.nextInt(maxInt);
                }
                oneNode.setParentName(Integer.toString(tmpName));
                ParentNames.add(tmpName);
                todoNode.addAll(oneNode.getChildren());
                todoNode.remove(oneNode);
            }
            todoNode.add(root);
            while (!todoNode.isEmpty()) {
                FormatNode oneNode = todoNode.get(0);

                if (oneNode.getParent() == null) {
                    writer.println(oneNode.getParentName() + ",  " + "ROOT" + ",  " +
                            "{name: " + oneNode.getName() + ", displayName: " + oneNode.getDisplayName() +
                            ", type: " + oneNode.getType() + ", range: " + oneNode.getRange() + "}");
                } else {
                    writer.println(oneNode.getParentName() + ",  " + oneNode.getParent().getParentName() + ",  " +
                            "{name: " + oneNode.getName() + ", displayName: " + oneNode.getDisplayName() +
                            ", type: " + oneNode.getType() + ", range: " + oneNode.getRange() + "}");
                }
                todoNode.addAll(oneNode.getChildren());
                todoNode.remove(oneNode);
            }

            writer.close();
        }
    }

    public static void Tree2Data(String inputString) throws Exception {
        Random rand = new SecureRandom();
        int maxInt = 99999999;


        FormatNode rootOrig = gson.fromJson(inputString, new TypeToken<FormatNode>() {
        }.getType());
        for (FormatNode oneProtocol : rootOrig.getChildren()) {

            FormatNode root = gson.fromJson(inputString, new TypeToken<FormatNode>() {
            }.getType());
            FormatNode myProtocol = null;
            for (FormatNode canProtocol : root.getChildren()) {
                if (canProtocol.name.equals(oneProtocol.name)) {
                    myProtocol = canProtocol;
                }
            }
            root.getChildren().clear();
            root.getChildren().add(myProtocol);

            addRemoveParent(root, false);
            List<FormatNode> todoNode = new ArrayList<>();
            todoNode.add(root);

            Set<Integer> ParentNames = new HashSet<>();
            int tmpName;

            while (!todoNode.isEmpty()) {
                FormatNode oneNode = todoNode.get(0);
                tmpName = rand.nextInt(maxInt);
                while (ParentNames.contains(tmpName)) {
                    tmpName = rand.nextInt(maxInt);
                }
                oneNode.setParentName(Integer.toString(tmpName));
                ParentNames.add(tmpName);
                todoNode.addAll(oneNode.getChildren());
                todoNode.remove(oneNode);
            }
            todoNode.add(root);
            while (!todoNode.isEmpty()) {
                FormatNode oneNode = todoNode.get(0);

                if (oneNode.getParent() == null) {
                    System.out.println(oneNode.getParentName() + ",  " + "ROOT" + ",  " +
                            "{name: " + oneNode.getName() + ", displayName: " + oneNode.getDisplayName() +
                            ", type: " + oneNode.getType() + ", range: " + oneNode.getRange() + "}");
                } else {
                    System.out.println(oneNode.getParentName() + ",  " + oneNode.getParent().getParentName() + ",  " +
                            "{name: " + oneNode.getName() + ", displayName: " + oneNode.getDisplayName() +
                            ", type: " + oneNode.getType() + ", openPorts: \"" + oneNode.getOpenPorts() +
                            "\", isL2: " + oneNode.getIsL2() +
                            ", range: " + oneNode.getRange() + "}");

                }
                todoNode.addAll(oneNode.getChildren());
                todoNode.remove(oneNode);
            }
        }
    }

    public static void Tree2Data(FormatNode root, String outFile) throws Exception {
        Random rand = new SecureRandom();
        int maxInt = 99999999;
        PrintWriter writer = new PrintWriter(outFile, "UTF-8");
        addRemoveParent(root, false);
        List<FormatNode> todoNode = new ArrayList<>();
        todoNode.add(root);

        Set<Integer> ParentNames = new HashSet<>();
        int tmpName;

        while (!todoNode.isEmpty()) {
            FormatNode oneNode = todoNode.get(0);
            tmpName = rand.nextInt(maxInt);
            while (ParentNames.contains(tmpName)) {
                tmpName = rand.nextInt(maxInt);
            }
            oneNode.setParentName(Integer.toString(tmpName));
            ParentNames.add(tmpName);
            todoNode.addAll(oneNode.getChildren());
            todoNode.remove(oneNode);
        }
        todoNode.add(root);
        while (!todoNode.isEmpty()) {
            FormatNode oneNode = todoNode.get(0);

            if (oneNode.getParent() == null) {
                writer.println(oneNode.getParentName() + ",  " + "ROOT" + ",  " +
                        "{name: " + oneNode.getName() + ", displayName: " + oneNode.getDisplayName() +
                        ", type: " + oneNode.getType() + ", range: " + oneNode.getRange() + "}");
            } else {
                writer.println(oneNode.getParentName() + ",  " + oneNode.getParent().getParentName() + ",  " +
                        "{name: " + oneNode.getName() + ", displayName: " + oneNode.getDisplayName() +
                        ", type: " + oneNode.getType() + ", range: " + oneNode.getRange() + "}");
            }
            todoNode.addAll(oneNode.getChildren());
            todoNode.remove(oneNode);
        }

        writer.close();
    }

    public static void generateProtocolKeyMap(FormatNode root) {
        addRemoveParent(root, false);
        enActive(root);
        List<FormatNode> todo = findLeave(root, true);
        List<String> tmpList = new ArrayList<>();
        boolean done = false;
        while (!done) {
            List<FormatNode> newTodo = new ArrayList<>();
            if (todo.isEmpty()) break;
            FormatNode oneNode = todo.get(0);
            if (oneNode.getParent() != null && oneNode.getParent().getParent() != null) {
                if (oneNode.getParent().getParent().getDisplayName().equals("协议")) {
                    if (oneNode.type.equals(NodeType.KEY)) {
                        if (oneNode.getActive()) {
                            System.out.println(oneNode.displayName + "  -->  " + oneNode.name);
                        } else {
                            oneNode.setActive(false);
                        }
                    }

                } else {
                    newTodo.add(oneNode.getParent().getParent());
                    tmpList.add(0, oneNode.getName());
                }
            }
            todo.remove(0);
            todo.addAll(0, newTodo);
        }
        addRemoveParent(root, true);
        deActive(root);
    }


}

