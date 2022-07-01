package sample;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class AVLNode
{
    AVLNode left, right;
    int data;
    int height;

    /* Constructor */
    public AVLNode()
    {
        left = null;
        right = null;
        data = 0;
        height = 0;
    }
    /* Constructor */
    public AVLNode(int n)
    {
        left = null;
        right = null;
        data = n;
        height = 0;
    }
}
class AVLTree {
    AVLNode root;


    /* Constructor */
    public AVLTree() {
        root = null;
    }

    /* Function to check if tree is empty */
    public boolean isEmpty() {
        return root == null;
    }

    /* Make the tree logically empty */
    public void makeEmpty() {
        root = null;
    }

    /* Function to insert data */
    public void insert(int data) {
        root = insert(data, root);
    }

    /* Function to get height of node */
    public int height(AVLNode t) {
        return t == null ? -1 : t.height;
    }

    /* Function to max of left/right node */
    public int max(int lhs, int rhs) {
        return Math.max(lhs, rhs);
    }

    /* Function to insert data recursively */
    public AVLNode insert(int x, AVLNode t) {
        if (t == null)
            t = new AVLNode(x);
        else if (x < t.data) {
            t.left = insert(x, t.left);
            if (height(t.left) - height(t.right) == 2)
                if (x < t.left.data)
                    t = rotateWithLeftChild(t);
                else
                    t = doubleWithLeftChild(t);
        } else if (x > t.data) {
            t.right = insert(x, t.right);
            if (height(t.right) - height(t.left) == 2)
                if (x > t.right.data)
                    t = rotateWithRightChild(t);
                else
                    t = doubleWithRightChild(t);
        } else
            ;  // Duplicate; do nothing
        t.height = max(height(t.left), height(t.right)) + 1;
        return t;
    }

    /* Rotate binary tree node with left child */
    public AVLNode rotateWithLeftChild(AVLNode k2) {
        AVLNode k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = max(height(k2.left), height(k2.right)) + 1;
        k1.height = max(height(k1.left), k2.height) + 1;
        return k1;
    }

    /* Rotate binary tree node with right child */
    public AVLNode rotateWithRightChild(AVLNode k1) {
        AVLNode k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = max(height(k1.left), height(k1.right)) + 1;
        k2.height = max(height(k2.right), k1.height) + 1;
        return k2;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child
     */
    public AVLNode doubleWithLeftChild(AVLNode k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child
     */
    public AVLNode doubleWithRightChild(AVLNode k1) {
        k1.right = rotateWithLeftChild(k1.right);
        return rotateWithRightChild(k1);
    }

    // Functions to count number of nodes
    public int countNodes() {
        return countNodes(root);
    }

    public int countNodes(AVLNode r) {
        if (r == null)
            return 0;
        else {
            int l = 1;
            l += countNodes(r.left);
            l += countNodes(r.right);
            return l;
        }
    }

    /* Functions to search for an element */
    public boolean search(int val) {
        return search(root, val);
    }

    public boolean search(AVLNode r, int val) {
        boolean found = false;
        while ((r != null) && !found) {
            int rval = r.data;
            if (val < rval)
                r = r.left;
            else if (val > rval)
                r = r.right;
            else {
                found = true;
                break;
            }
            found = search(r, val);
        }
        return found;
    }

    //Function for inorder traversal */
    /*public void inorder()
    {
        inorder(root);
    }
    public void inorder(AVLNode r)
    {
        if (r != null)
        {
            inorder(r.left);
            System.out.print(r.data +" ");
            inorder(r.right);
        }
    }
    /* Function for preorder traversal */

    public List<Integer> inorderTraversal(AVLNode root) {
        ArrayList<Integer> result = new ArrayList<>();
        Stack<AVLNode> stack = new Stack<>();

        AVLNode p = root;
        while (p != null) {
            stack.push(p);
            p = p.left;
        }

        while (!stack.isEmpty()) {
            AVLNode t = stack.pop();
            result.add(t.data);

            t = t.right;
            while (t != null) {
                stack.push(t);
                t = t.left;
            }
        }

        return result;
    }

    public void preorder() {
        preorder(root);
    }

    public void preorder(AVLNode r) {
        if (r != null) {
            System.out.print(r.data + " ");
            preorder(r.left);
            preorder(r.right);
        }
    }

    //Function for postorder traversal
    public void postorder() {
        ArrayList<Integer> order = new ArrayList<>();
        postorder(root, order);
    }

    public ArrayList<Integer> postorder(AVLNode r, List list) {

        ArrayList<Integer> order = new ArrayList<>();
        if (r != null) {

            postorder(r.left, order);
            postorder(r.right, order);
            int z = r.data;
            //System.out.print(r.data + " ");
            order.add(z);

        }
        return order;
    }


    public AVLNode deleteNode(AVLNode root, int key) {
        // STEP 1: PERFORM STANDARD BST DELETE
        if (root == null)
            return root;

        // If the key to be deleted is smaller than
        // the root's key, then it lies in left subtree
        if (key < root.data)
            root.left = deleteNode(root.left, key);

            // If the key to be deleted is greater than the
            // root's key, then it lies in right subtree
        else if (key > root.data)
            root.right = deleteNode(root.right, key);

            // if key is same as root's key, then this is the node
            // to be deleted
        else {

            // node with only one child or no child
            if ((root.left == null) || (root.right == null)) {
                AVLNode temp = null;
                if (temp == root.left)
                    temp = root.right;
                else
                    temp = root.left;

                // No child case
                if (temp == null) {
                    temp = root;
                    root = null;
                } else // One child case
                    root = temp; // Copy the contents of
                // the non-empty child
            } else {

                // node with two children: Get the inorder
                // successor (smallest in the right subtree)
                AVLNode temp = minValueNode(root.right);

                // Copy the inorder successor's data to this node
                root.data = temp.data;

                // Delete the inorder successor
                root.right = deleteNode(root.right, temp.data);
            }
        }

        // If the tree had only one node then return
        if (root == null)
            return root;

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        root.height = max2(height(root.left), height(root.right)) + 1;

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
        // this node became unbalanced)
        int balance = getBalance(root);

        // If this node becomes unbalanced, then there are 4 cases
        // Left Left Case
        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        // Left Right Case
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        // Right Left Case
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    public AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;

        /* loop down to find the leftmost leaf */
        while (current.left != null)
            current = current.left;

        return current;
    }

    // A utility function to get maximum of two integers
    public int max2(int a, int b) {
        return Math.max(a, b);
    }

    // A utility function to right rotate subtree rooted with y
    // See the diagram given above.
    public AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = max2(height(y.left), height(y.right)) + 1;
        x.height = max2(height(x.left), height(x.right)) + 1;

        // Return new root
        return x;
    }

    // A utility function to left rotate subtree rooted with x
    // See the diagram given above.
    public AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = max2(height(x.left), height(x.right)) + 1;
        y.height = max2(height(y.left), height(y.right)) + 1;

        // Return new root
        return y;
    }

    // Get Balance factor of node N
    public int getBalance(AVLNode N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }


    static class AVLView extends Pane {
        AVLTree tree = new AVLTree();
        double radius = 20;
        double vGap = 55;

        AVLView(AVLTree tree) {
            this.tree = tree;
            setStatus("NO TREE FOUND");
        }


        public void setStatus(String message) {
            getChildren().add(new Text(25, 25, message));
        }


        void displayAVLTree(int key) {
            this.getChildren().clear();
            if (tree.root != null) {
                displayAVLTree(tree.root, getWidth() / 2, vGap, getWidth() / 4, key);
            }
        }

        private void displayAVLTree(AVLNode root, double x, double y, double hGap, int key) {

            Circle circle = new Circle(x, y, radius);
            if (root.left != null) {
                // Draw a line to the left node
                getChildren().add(new Line(x - hGap, y + vGap, x, y));
                // Draw the left subtree recursively
                displayAVLTree(root.left, x - hGap, y + vGap, hGap / 2, key);
            }

            if (root.right != null) {
                // Draw a line to the right node
                getChildren().add(new Line(x + hGap, y + vGap, x, y));
                // Draw the right subtree recursively
                displayAVLTree(root.right, x + hGap, y + vGap, hGap / 2, key);
            }

            circle.setFill(Color.SALMON);
            circle.setStroke(Color.BLACK);
            getChildren().addAll(circle, new Text(x - 5, y + 5, root.data + ""));

        }

        void displayAVLTre(int key) {
            this.getChildren().clear();
            if (tree.root != null) {
                displayAVLTre(tree.root, getWidth() / 2, vGap, getWidth() / 4, key);
            }
        }

        private void displayAVLTre(AVLNode root, double x, double y, double hGap, int key) {

            Circle circle = new Circle(x, y, radius);
            if (root.left != null) {
                // Draw a line to the left node
                getChildren().add(new Line(x - hGap, y + vGap, x, y));
                // Draw the left subtree recursively
                displayAVLTre(root.left, x - hGap, y + vGap, hGap / 2, key);
            }

            if (root.right != null) {
                // Draw a line to the right node
                getChildren().add(new Line(x + hGap, y + vGap, x, y));
                // Draw the right subtree recursively
                displayAVLTre(root.right, x + hGap, y + vGap, hGap / 2, key);
            }

            circle.setFill(Color.YELLOW);
            if (root.data == key) {
                circle.setFill(Color.RED);
            }

            circle.setStroke(Color.BLACK);

            getChildren().addAll(circle, new Text(x - 5, y + 5, root.data + ""));

        }
    }




}

public class Main extends Application {

    private Object NodeObject;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("AVL Tree");
        // AVLTree tree=new AVLTree();


        AVLTree AVL = new AVLTree();
        AVLTree.AVLView treeview=new AVLTree.AVLView(AVL);
        AVL.root = null;
        BorderPane bp = new BorderPane();
        Pane pane = new Pane();
        bp.setCenter(pane);
        bp.setStyle("-fx-background-color: #EEECDA");
        HBox hb1 = new HBox();
        bp.setTop(hb1);
        HBox hb2 = new HBox();
        bp.setBottom(hb2);
        TextField tf1 = new TextField();
        Button bt1 = new Button("INSERT");
        bt1.setFont(new Font("BRITANNIC BOLD", 16));
        bt1.setTextFill(Color.web("#FFFFFF"));
        Label lb3 = new Label();
        int h = AVL.height(AVL.root);
        int no = AVL.countNodes(AVL.root);
        Label lb4 = new Label();
        Label lb5 = new Label();
        lb4.setText("Number of Vertices: " + no);
        lb3.setText("Current height: " + h);
        lb3.setTextFill(Color.web("#FFFFFF"));
        lb4.setTextFill(Color.web("#FFFFFF"));

        lb3.setFont(new Font("BRITANNIC BOLD", 16));
        lb4.setFont(new Font("BRITANNIC BOLD", 16));




        lb3.setPadding(new Insets(50, 0, 0, 0));
        lb4.setPadding(new Insets(50, 0, 0, 300));
        // when insert is pressed, following event takes place
        bt1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Label l = new Label("no text");
                AVLNode N = new AVLNode();
                l.setText(tf1.getText());

                if (l.getText() != null) {
                    int n = Integer.parseInt(l.getText());

                    AVL.insert(n);
                    treeview.displayAVLTree(n);
                    tf1.setText("");

                    List<Integer> order1 = new ArrayList<>();
                    order1 = AVL.inorderTraversal(AVL.root);
                    System.out.println(order1);


                }
                int h = AVL.height(AVL.root);
                lb3.setText("Current height: " + h);
                int no = AVL.countNodes(AVL.root);
                lb4.setText("Number of Vertices: " + no);


            }
        });
        TextField tf2 = new TextField();
        Button bt2 = new Button("FIND");
        bt2.setFont(new Font("BRITANNIC BOLD", 16));
        bt2.setTextFill(Color.web("#FFFFFF"));
        //when find is pressed,following event takes place
        bt2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Label l = new Label("no text");
                AVLNode N = new AVLNode();
                l.setText(tf2.getText());
                Alert a = new Alert(Alert.AlertType.NONE);
                if (l.getText() != null) {
                    int n = Integer.parseInt(l.getText());
                    if (AVL.search(n) == true) {
                        treeview.displayAVLTre(Integer.parseInt(tf2.getText()));
                        a.setAlertType(Alert.AlertType.INFORMATION);
                        a.setContentText("Node " + n + " was found");
                        a.show();
                        tf2.setText("");
                    } else if (AVL.search(n) == false) {
                        a.setAlertType(Alert.AlertType.INFORMATION);
                        a.setContentText("Node " + n + " was not found");
                        a.show();
                        tf2.setText("");
                    }

                }
            }
        });
        TextField tf3 = new TextField();
        Button bt3 = new Button("DELETE");
        bt3.setFont(new Font("BRITANNIC BOLD", 16));
        bt3.setTextFill(Color.web("#FFFFFF"));
        //when delete is pressed, following event takes place
        bt3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (tf3.getText() != null) {
                    int n = Integer.parseInt(tf3.getText());
                    AVL.root = AVL.deleteNode(AVL.root, n);
                }
                treeview.displayAVLTree(Integer.parseInt(tf3.getText()));
                int h = AVL.height(AVL.root);
                lb3.setText("Current height: " + h);
                int no = AVL.countNodes(AVL.root);
                lb4.setText("Number of Vertices: " + no);
                tf3.setText("");


            }
        });
        Button bt4 = new Button("PRINT INORDER");
        bt4.setFont(new Font("BRITANNIC BOLD", 16));
        bt4.setTextFill(Color.web("#FFFFFF"));
        Label lb6=new Label();
        bt4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                List<Integer> arr=new ArrayList<>();
                arr=AVL.inorderTraversal(AVL.root);
                lb6.setText("Inorder Traversal: " +arr );
                lb6.setPadding(new Insets(0,0,0,-570));
                lb6.setTextFill(Color.web("#FFFFFF"));

                lb6.setFont(new Font("BRITANNIC BOLD", 16));


                hb2.getChildren().addAll(lb6);


            }
        });
        hb2.getChildren().remove(lb6);

        Label lb1 = new Label();
        lb1.setPadding(new Insets(200, 0, 0, 0));
        Label lb2 = new Label();
        lb2.setPadding(new Insets(50, 0, 0, 0));

        VBox vb1 = new VBox();
        bp.setRight(vb1);
        VBox vb2 = new VBox();
        bp.setLeft(vb2);

        vb1.getChildren().addAll(lb2, tf1, bt1, tf2, bt2, tf3, bt3);
        vb2.getChildren().addAll(lb1, bt4);
        //for bt1
        bt1.setMaxSize(150, 150);
        bt2.setMaxSize(150, 150);
        bt3.setMaxSize(150, 150);
        // bt4.setMaxSize(150,150);
        bt4.setMinWidth(150);
        tf1.setMinSize(5, 5);
        tf2.setMinSize(5, 5);
        tf3.setMinSize(5, 5);
        vb1.setStyle("-fx-background-color: #FF847C;");
        vb2.setStyle("-fx-background-color: #FF847C ;");
        bt1.setStyle("-fx-background-color: #2A363B ;");
        // bt1.setPadding(new Insets(10,5,5,5));

        bt2.setStyle("-fx-background-color: #2A363B ;");
        bt3.setStyle("-fx-background-color: #2A363B ;");
        bt4.setStyle("-fx-background-color: #2A363B ;");
        tf1.setMaxSize(150, 150);
        vb1.setMaxSize(1000, 1000);
        vb2.setMaxSize(1000, 1000);
        vb1.setSpacing(35);
        Label lb = new Label("AVL Tree");
        lb.setMinSize(50, 50);
        lb.setAlignment(Pos.CENTER);
        lb.setFont(new Font("BRITANNIC BOLD", 30));
        lb.setTextFill(Color.web("#2A363B"));
        lb.setPadding(new Insets(0, 0, 0, 280));
        hb1.getChildren().addAll(lb);




        hb2.getChildren().addAll(lb3, lb4);

        hb1.setStyle("-fx-background-color: #E84A5F ;");
        hb2.setStyle("-fx-background-color: #E84A5F ;");
        Scene scene = new Scene(bp, 800, 600);
        //scene.setFill(Color.BLUE);
        primaryStage.setScene(scene);
        bp.setCenter(treeview);
        //Displaying the contents of the stage
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }}