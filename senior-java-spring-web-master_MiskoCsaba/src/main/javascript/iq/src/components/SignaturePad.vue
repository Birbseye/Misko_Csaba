<template >

  <div id="app" >
<!--    <vueSignature ref="signature" :sigOption="option" :w="'800px'" :h="'400px'" :disabled="disabled" :defaultUrl="dataUrl"></vueSignature>-->
    <vueSignature ref="signature" :sigOption="option"></vueSignature>
    <button @click="save">Save</button>
    <button @click="clear">Clear Sign</button>
    <button @click="undo">Undo</button>
  </div>

</template>

<script>
import vueSignature from "vue-signature"
// import Canvas from "@/components/Canvas.vue";
export default {
  name: "app",
  components:{
    // Canvas,
    vueSignature
  },
  data() {
    return {
      option:{
        penColor:"rgb(0, 0, 0)",
        backgroundColor:"antiquewhite"
      }
    };
  },
  methods:{
    save(){
      let _this = this;
      let png = _this.$refs.signature.save()
      let jpeg = _this.$refs.signature.save('image/jpeg')
      let svg = _this.$refs.signature.save('image/svg+xml');
      console.log(png);
      console.log(jpeg)
      console.log(svg)
    },
    clear(){
      let _this = this;
      _this.$refs.signature.clear();
    },
    undo(){
      let _this = this;
      _this.$refs.signature.undo();
    },
    fromDataURL(url){
      let _this = this;
      _this.$refs.signature.fromDataURL("base64");
    },
    handleDisabled(){
      let _this = this;
      _this.disabled  = !_this.disabled
    }
  }
};
</script>

<style lang="scss">

    .canvas {
      margin-top: 20px;
      border: 2px solid black;
      border-radius: 5px;
      width: 60%;
      height: 40%;
    }

    button{
      margin: 8px;
    }

</style>
