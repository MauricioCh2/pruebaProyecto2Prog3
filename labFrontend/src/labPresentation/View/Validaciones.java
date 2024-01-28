package labPresentation.View;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class Validaciones {
    //insert valida los elementos escritos
    //replace los elementos remplazados, como copiados y pegados o cambiados por el propio codigo
    public class ValidarSinEspeciales extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            // Permitir la inserción de caracteres numéricos
            if (isNotEspecial(string)) {
                super.insertString(fb, offset, string, attr);
            } else {
                // Show a message or take other action for invalid input
                JOptionPane.showMessageDialog(null, "Por favor no ingrese caracteres especiales", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            // Permitir la sustitución de caracteres existentes con caracteres numéricos
            if (isNotEspecial(text)) {
                super.replace(fb, offset, length, text, attrs);
            } else if (text.isEmpty()) {
                // Permitir la eliminación de caracteres
                super.replace(fb, offset, length, text, attrs);
            } else {
                // Show a message or take other action for invalid input
                JOptionPane.showMessageDialog(null, "Por favor no ingrese caracteres especiales ", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//valida que no se ingrese especiales
    public class ValidarSinEspecialesWSpace extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            // Permitir la inserción de caracteres numéricos
            if (isNotEspecialwhitSpace(string)) {
                super.insertString(fb, offset, string, attr);
            } else {
                // Show a message or take other action for invalid input
                JOptionPane.showMessageDialog(null, "Por favor no ingrese caracteres especiales", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            // Permitir la sustitución de caracteres existentes con caracteres numéricos
            if (isNotEspecialwhitSpace(text)) {
                super.replace(fb, offset, length, text, attrs);
            } else if (text.isEmpty()) {
                // Permitir la eliminación de caracteres
                super.replace(fb, offset, length, text, attrs);
            } else {
                // Show a message or take other action for invalid input
                JOptionPane.showMessageDialog(null, "Por favor no ingrese caracteres especiales ", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//valida que no se ingrese especiales
    public class ValidarVacio extends DocumentFilter{
        @Override
        public void insertString(FilterBypass fb, int offset, String str, AttributeSet attr) throws BadLocationException {
            // Si el texto a insertar es vacío, no se inserta
            if (str.isEmpty()) {
                return;
            }

            // Se llama al método insertString() del padre
            super.insertString(fb, offset, str, attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String str, AttributeSet attr) throws BadLocationException {
            // Si el texto a reemplazar es vacío, no se reemplaza
            if (str.isEmpty()) {
                return;
            }

            // Se llama al método replace() del padre
            super.replace(fb, offset, length, str, attr);
        }
    }
    public class ValidarOnlyNum extends DocumentFilter{
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            // Permitir la inserción de caracteres numéricos
            if (isNumeric(string)) {
                super.insertString(fb, offset, string, attr);
            } else {
                // Show a message or take other action for invalid input
                JOptionPane.showMessageDialog(null, "Por favor ingrese solo valores numericos", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            // Permitir la sustitución de caracteres existentes con caracteres numéricos
            if (isNumeric(text)) {
                super.replace(fb, offset, length, text, attrs);
            } else if (text.isEmpty()) {
                // Permitir la eliminación de caracteres
                super.replace(fb, offset, length, text, attrs);
            } else {
                // Show a message or take other action for invalid input
                JOptionPane.showMessageDialog(null, "Por favor ingrese solo valores numericos ", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }


    }
    public class ValidarOnlyCharact extends DocumentFilter{
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            // Permitir la inserción de caracteres numéricos
            if (isLetters(string)) {
                super.insertString(fb, offset, string, attr);
            } else {
                // Show a message or take other action for invalid input
                JOptionPane.showMessageDialog(null, "Por favor, no ingrese numeros o caracteres especiales ", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            // Permitir la sustitución de caracteres existentes con caracteres numéricos
            if (isLetters(text)) {
                super.replace(fb, offset, length, text, attrs);
            } else if (text.isEmpty()) {
                // Permitir la eliminación de caracteres
                super.replace(fb, offset, length, text, attrs);
            } else {
                // Show a message or take other action for invalid input
                JOptionPane.showMessageDialog(null, "Por favor, no ingrese numeros o caracteres especiales", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }



    }

    public class ValidarSinEspecialesWithThat extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            // Permitir la inserción de caracteres numéricos
            if (isNotEspecialwhitthat(string)) {
                super.insertString(fb, offset, string, attr);
            } else {
                // Show a message or take other action for invalid input
                JOptionPane.showMessageDialog(null, "Por favor no ingrese caracteres especiales", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            // Permitir la sustitución de caracteres existentes con caracteres numéricos
            if (isNotEspecialwhitthat(text)) {
                super.replace(fb, offset, length, text, attrs);
            } else if (text.isEmpty()) {
                // Permitir la eliminación de caracteres
                super.replace(fb, offset, length, text, attrs);
            } else {
                // Show a message or take other action for invalid input
                JOptionPane.showMessageDialog(null, "Por favor no ingrese caracteres especiales ", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//valida que no se ingrese

    public class ValidarOnlyNumWithThat extends DocumentFilter{
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            // Permitir la inserción de caracteres numéricos
            if (isNumericWithThat(string)) {
                super.insertString(fb, offset, string, attr);
            } else {
                // Show a message or take other action for invalid input
                JOptionPane.showMessageDialog(null, "Por favor ingrese solo valores numericos", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            // Permitir la sustitución de caracteres existentes con caracteres numéricos
            if (isNumericWithThat(text)) {
                super.replace(fb, offset, length, text, attrs);
            } else if (text.isEmpty()) {
                // Permitir la eliminación de caracteres
                super.replace(fb, offset, length, text, attrs);
            } else {
                // Show a message or take other action for invalid input
                JOptionPane.showMessageDialog(null, "Por favor ingrese solo valores numericos ", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }


    }
    public class ValidarOnlyNumWithNegative extends DocumentFilter{
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            // Permitir la inserción de caracteres numéricos
            if (isNumericWithNegative(string)) {
                super.insertString(fb, offset, string, attr);
            } else {
                // Show a message or take other action for invalid input
                JOptionPane.showMessageDialog(null, "Por favor ingrese solo valores numericos", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            // Permitir la sustitución de caracteres existentes con caracteres numéricos
            if (isNumericWithNegative(text)) {
                super.replace(fb, offset, length, text, attrs);
            } else if (text.isEmpty()) {
                // Permitir la eliminación de caracteres
                super.replace(fb, offset, length, text, attrs);
            } else {
                // Show a message or take other action for invalid input
                JOptionPane.showMessageDialog(null, "Por favor ingrese solo valores numericos ", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }


    }
    private boolean isNumeric(String text) {
        return text.matches("\\d*"); //Revis si solo contiene caracteres numericos
    }
    private boolean isLetters(String text) {
        return text.matches("^[a-zA-Z]+$"); // Revisa si solo se ingresa letras
    }
    private boolean isNotEspecial(String text) { //con .
        return text.matches("^[a-zA-Z0-9]+$"); // Revisa que no tenga caracteres especiales
    }
    private boolean isNotEspecialwhitSpace(String text) {
        return text.matches("^[a-zA-Z0-9\\s]+$"); // Revisa que no tenga caracteres especiales cambie el $ por +
    }
    private boolean isNotEspecialwhitthat(String text) {
        return text.matches("^[.a-zA-Z0-9]+$"); // Revisa que no tenga caracteres especiales
    }
    private boolean isNumericWithThat(String text) {
        return text.matches("-?\\d+(\\.\\d+)?");  //Revis si solo contiene caracteres numericos
    }
    private boolean isNumericWithNegative(String text) {
        return text.matches("^-?\\d*"); //Revis si solo contiene caracteres numericos
    }

}